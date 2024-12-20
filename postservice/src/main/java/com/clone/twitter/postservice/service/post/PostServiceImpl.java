package com.clone.twitter.postservice.service.post;

import com.clone.twitter.postservice.config.moderation.ModerationDictionary;
import com.clone.twitter.postservice.dto.post.PostCreateDto;
import com.clone.twitter.postservice.dto.post.PostDto;
import com.clone.twitter.postservice.dto.post.PostHashtagDto;
import com.clone.twitter.postservice.dto.post.PostUpdateDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.VerificationStatus;
import com.clone.twitter.postservice.exception.NotFoundException;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.producer.post.PostProducer;
import com.clone.twitter.postservice.kafka.producer.post.PostViewProducer;
import com.clone.twitter.postservice.mapper.post.PostMapper;
import com.clone.twitter.postservice.repository.post.PostRepository;
import com.clone.twitter.postservice.service.hashtag.async.AsyncHashtagService;
import com.clone.twitter.postservice.service.spelling.SpellingService;
import com.clone.twitter.postservice.validator.post.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostValidator postValidator;
    private final AsyncHashtagService asyncHashtagService;
    private final ModerationDictionary moderationDictionary;
    private final SpellingService spellingService;
    private final PostProducer postProducer;
    private final PostViewProducer postViewProducer;

    @Override
    @Transactional
    public PostDto findById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id %s not found", id)));

        post.setViewsCount(post.getViewsCount() + 1);

        postViewProducer.produce(postMapper.toViewKafkaEvent(post));

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto create(PostCreateDto postCreateDto) {
        postValidator.validateAuthor(postCreateDto.getAuthorId(), postCreateDto.getProjectId());
        postValidator.validatePostContent(postCreateDto.getContent());
        Post post = postRepository.save(postMapper.toEntity(postCreateDto));
        return postMapper.toDto(post);
    }

        @Override
    @Transactional
    public PostDto publish(Long id) {
        Post post = findPostById(id);
        postValidator.validatePublicationPost(post);
        post.setPublished(true);
        post.setPublishedAt(LocalDateTime.now());
        final Post entity = postRepository.save(post);

        PostHashtagDto postHashtagDto = postMapper.toHashtagDto(entity);
        asyncHashtagService.addHashtags(postHashtagDto);

        sendMessageToKafka(post, State.ADD);

        return postMapper.toDto(entity);
    }

    @Override
    @Transactional
    public PostDto update(Long id, PostUpdateDto postUpdateDto) {
        Post post = findPostById(id);
        postValidator.validatePostContent(post.getContent());
        post.setContent(postUpdateDto.getContent());
        post = postRepository.save(post);

        PostHashtagDto postHashtagDto = postMapper.toHashtagDto(post);
        asyncHashtagService.updateHashtags(postHashtagDto);

        sendMessageToKafka(post, State.UPDATE);

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Post post = findPostById(id);
        post.setDeleted(true);
        postRepository.save(post);

        PostHashtagDto postHashtagDto = postMapper.toHashtagDto(post);
        asyncHashtagService.removeHashtags(postHashtagDto);

        sendMessageToKafka(post, State.DELETE);
    }

    @Override
    public List<PostDto> findAllByHashtag(String hashtag, Pageable pageable) {
        return asyncHashtagService.getPostsByHashtag(hashtag, pageable).join().stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> findPostDraftsByUserAuthorId(Long id) {
        return postRepository.findByAuthorIdAndPublishedAndDeletedWithLikes(id, false, false).stream()
                .map(postMapper::toDto)
                .sorted(Comparator.comparing(PostDto::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<PostDto> findPostDraftsByProjectAuthorId(Long id) {
        return postRepository.findByProjectIdAndPublishedAndDeletedWithLikes(id, false, false).stream()
                .map(postMapper::toDto)
                .sorted(Comparator.comparing(PostDto::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<PostDto> findPostPublicationsByUserAuthorId(Long id) {
        return postRepository.findByAuthorIdAndPublishedAndDeletedWithLikes(id, true, false).stream()
                .map(postMapper::toDto)
                .sorted(Comparator.comparing(PostDto::getPublishedAt).reversed())
                .toList();
    }

    @Override
    public List<PostDto> findPostPublicationsByProjectAuthorId(Long id) {
        return postRepository.findByProjectIdAndPublishedAndDeletedWithLikes(id, true, false).stream()
                .map(postMapper::toDto)
                .sorted(Comparator.comparing(PostDto::getPublishedAt).reversed())
                .toList();
    }

    @Override
    @Async("executorService")
    public void verifyPost(List<Post> posts) {
        for (Post post : posts) {

            if (moderationDictionary.checkCurseWordsInPost(post.getContent())) {
                post.setIsVerify(VerificationStatus.NOT_VERIFIED);
            } else {
                post.setIsVerify(VerificationStatus.VERIFIED);
            }

            post.setVerifiedDate(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    @Override
    public List<Long> findAllAuthorIdsWithNotVerifiedPosts() {
        return postRepository.findAllNotVerifiedPosts().stream()
                .map(Post::getAuthorId)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void correctPosts(){
        List<Post> unpublishedPosts = postRepository.findReadyToPublish();
        Map<Post, CompletableFuture<Optional<String>>> correctedContents = new HashMap<>();
        unpublishedPosts.stream()
                .filter(post -> !post.isCheckedForSpelling())
                .forEach(post->correctedContents.put(post, spellingService.checkSpelling(post.getContent())));

        correctedContents.forEach((post, correctedContent)->{
            try {
                correctedContent.get().ifPresent((content) -> {
                    post.setContent(content);
                    post.setCheckedForSpelling(true);
                });
            }
            catch (InterruptedException | ExecutionException e) {
                log.error("Error when updating a post with an id: {}", post.getId(), e);
                throw new RuntimeException(e);
            }
        });
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id %s not found", id)));
    }

    private void sendMessageToKafka(Post post, State state) {
        if (post.getAuthorId() != null) {
            postProducer.produce(postMapper.toKafkaEvent(post, state));
        }
    }
}