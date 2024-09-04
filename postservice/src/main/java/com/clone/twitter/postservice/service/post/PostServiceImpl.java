package com.clone.twitter.postservice.service.post;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.clone.twitter.postservice.dto.PostDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.producer.post.PostProducer;
import com.clone.twitter.postservice.kafka.producer.post.PostViewProducer;
import com.clone.twitter.postservice.mapper.PostMapper;
import com.clone.twitter.postservice.repository.post.PostRepository;
import com.clone.twitter.postservice.validator.post.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostValidator postValidator;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
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
        postValidator.validateAuthor(postDto);
        Post post = postMapper.toEntity(postDto);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto publishPost(long postId) {
        Post post = existsPost(postId);
        postValidator.checkPostAuthorship(post);
        postValidator.isPublishedPost(post);
        post.setPublished(true);
        post.setPublishedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = existsPost(postId);
        postValidator.checkPostAuthorship(post);
        post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto deletePost(long postId) {
        Post post = existsPost(postId);
        postValidator.checkPostAuthorship(post);
        postValidator.isDeletedPost(post);
        post.setPublished(false);
        post.setDeleted(true);
        return postMapper.toDto(post);
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
    public Post existsPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new DataValidationException("Post with ID " + postId + " not found"));
    }
}