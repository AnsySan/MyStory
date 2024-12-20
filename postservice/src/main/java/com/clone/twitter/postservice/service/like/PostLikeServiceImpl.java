package com.clone.twitter.postservice.service.like;

import com.clone.twitter.postservice.dto.like.PostLikeDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.PostLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.event.like.PostLikeKafkaEvent;
import com.clone.twitter.postservice.kafka.producer.like.PostLikeProducer;
import com.clone.twitter.postservice.mapper.like.PostLikeMapper;
import com.clone.twitter.postservice.redis.publisher.LikeEventPublisher;
import com.clone.twitter.postservice.redis.publisher.event.LikeRedisEvent;
import com.clone.twitter.postservice.repository.like.PostLikeRepository;
import com.clone.twitter.postservice.validator.like.LikeValidator;
import com.clone.twitter.postservice.validator.like.LikeValidatorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImpl implements PostLikeService<PostLikeDto> {

    private final PostLikeRepository postLikeRepository;
    private final PostLikeMapper postLikeMapper;
    private final LikeValidator likeValidator;
    private final LikeEventPublisher likeEventPublisher;
    private final PostLikeProducer postLikeProducer;

    @Override
    @Transactional
    public PostLikeDto addLikeToPost(long userId, long id) {

        PostLikeDto likeDto = createLikeDto(userId, id);

        likeValidator.validateUserExistence(userId);
        Post post = likeValidator.validateAndGetPostToLike(userId, id);

        PostLike like = postLikeMapper.toEntity(likeDto);
        like.setPost(post);
        like = postLikeRepository.save(like);

        likeEventPublisher.publish(new LikeRedisEvent(id, post.getAuthorId(), userId, LocalDateTime.now()));
        PostLikeKafkaEvent kafkaEvent = postLikeMapper.toKafkaEvent(like, State.ADD);
        postLikeProducer.produce(kafkaEvent);

        log.info("Like with likeId = {} was added on post with postId = {} by user with userId = {}", like.getId(), id, userId);

        return postLikeMapper.toDto(like);
    }

    @Override
    @Transactional
    public void removeLikeToPost(long userId, long id) {

        PostLikeDto likeDto = createLikeDto(userId, id);
        PostLike like = postLikeMapper.toEntity(likeDto);

        postLikeRepository.deleteByPostIdAndUserId(id, userId);

        postLikeProducer.produce(postLikeMapper.toKafkaEvent(like, State.DELETE));

        log.info("Like with likeId = {} was removed from post with postId = {} by user with userId = {}", like.getId(), id, userId);
    }

    private PostLikeDto createLikeDto(Long userId, Long postId) {
        PostLikeDto likeDto = new PostLikeDto();
        likeDto.setUserId(userId);
        likeDto.setPostId(postId);
        return likeDto;
    }
}