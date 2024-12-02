package com.clone.twitter.postservice.service.post;

import com.clone.twitter.postservice.config.moderation.ModerationDictionary;
import com.clone.twitter.postservice.dto.post.PostDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.VerificationStatus;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.exception.NotFoundException;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.producer.post.PostProducer;
import com.clone.twitter.postservice.kafka.producer.post.PostViewProducer;
import com.clone.twitter.postservice.mapper.post.PostMapper;
import com.clone.twitter.postservice.repository.post.PostRepository;
import com.clone.twitter.postservice.service.hashtag.async.AsyncHashtagService;
import com.clone.twitter.postservice.service.spelling.SpellingService;
import com.clone.twitter.postservice.validator.post.PostValidatorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostValidatorImpl postValidatorImpl;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final AsyncHashtagService asyncHashtagService;
    private final ModerationDictionary moderationDictionary;
    private final SpellingService spellingService;
    private final PostProducer postProducer;
    private final PostViewProducer postViewProducer;


    @Override
    public PostDto findById(long id) {

        Post post =  postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id %s not found", id)));

        postViewProducer.produce(postMapper.toViewKafkaEvent(post));

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto createPost(PostDto postDto) {
        postValidatorImpl.validateAuthor(postDto.getAuthorId());
        Post post = postMapper.toEntity(postDto);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto publishPost(long postId) {
        Post post = existsPost(postId);
        postValidatorImpl.checkPostAuthorship(post);
        postValidatorImpl.validatePublicationPost(post);
        post.setPublished(true);
        post.setPublishedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = existsPost(postId);
        postValidatorImpl.checkPostAuthorship(post);
        post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto deletePost(long postId) {
        Post post = existsPost(postId);
        postValidatorImpl.isDeletedPost(post);
        post.setPublished(false);
        post.setDeleted(true);
        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> findAllByHashtag(String hashtag, Pageable pageable) {
        return asyncHashtagService.getPostsByHashtag(hashtag, pageable).join().stream()
                .map(postMapper::toDto)
                .toList();
    }

    private void sendMessageToKafka(Post post, State state) {
        if (post.getAuthorId() != null) {
            List<Long> subscriberIds = postRepository.getAuthorSubscriberIds(post.getAuthorId());
            postProducer.produce(postMapper.toKafkaEvent(post, subscriberIds, state));
        }
    }

    @Override
    public List<PostDto> findPostDraftsByUserAuthorId(long id) {
        return postRepository.findByAuthorIdAndPublishedAndDeletedWithLikes(id, false, false).stream()
                .map(postMapper::toDto)
                .sorted(Comparator.comparing(PostDto::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<PostDto> findPostPublicationsByUserAuthorId(long id) {
        return postRepository.findByAuthorIdAndPublishedAndDeletedWithLikes(id, true, false).stream()
                .map(postMapper::toDto)
                .sorted(Comparator.comparing(PostDto::getPublishedAt).reversed())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(long postId) {
        return postMapper.toDto(existsPost(postId));
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

    private Post existsPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new DataValidationException("Post with ID " + postId + " not found"));
    }
}