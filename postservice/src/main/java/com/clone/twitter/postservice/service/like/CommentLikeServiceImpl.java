package com.clone.twitter.postservice.service.like;

import com.clone.twitter.postservice.dto.CommentLikeDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.CommentLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.producer.like.CommentLikeProducer;
import com.clone.twitter.postservice.mapper.CommentLikeMapper;
import com.clone.twitter.postservice.repository.like.CommentLikeRepository;
import com.clone.twitter.postservice.validator.like.LikeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentLikeServiceImpl implements LikeService<CommentLikeDto> {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentLikeMapper commentLikeMapper;
    private final LikeValidator likeValidator;
    private final CommentLikeProducer commentLikeProducer;

    @Override
    public CommentLikeDto addLike(long userId, long id) {

        CommentLikeDto likeDto = createLikeDto(userId, id);

        likeValidator.validateUserExistence(userId);
        Comment comment = likeValidator.validateCommentToLike(userId, id);

        CommentLike like = commentLikeMapper.toEntity(likeDto);
        like.setComment(comment);
        like = commentLikeRepository.save(like);

        commentLikeProducer.produce(commentLikeMapper.toKafkaEvent(like, State.ADD));

        log.info("Like with likeId = {} was added on comment with commentId = {} by user with userId = {}", like.getId(), id, userId);

        return commentLikeMapper.toDto(like);
    }

    @Override
    public void removeLike(long userId, long id) {

        CommentLikeDto likeDto = createLikeDto(userId, id);
        CommentLike like = commentLikeMapper.toEntity(likeDto);

        commentLikeRepository.deleteByCommentIdAndUserId(id, userId);

        commentLikeProducer.produce(commentLikeMapper.toKafkaEvent(like, State.DELETE));

        log.info("Like with likeId = {} was removed from comment with commentId = {} by user with userId = {}", like.getId(), id, userId);
    }

    private CommentLikeDto createLikeDto(Long userId, Long commentId) {
        CommentLikeDto likeDto = new CommentLikeDto();
        likeDto.setUserId(userId);
        likeDto.setCommentId(commentId);
        return likeDto;
    }
}