package com.clone.twitter.postservice.service;

import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.LikeDto;
import com.clone.twitter.postservice.dto.UserDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Like;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.event.LikeEvent;
import com.clone.twitter.postservice.mapper.LikeMapper;
import com.clone.twitter.postservice.publisher.LikeEventPublisher;
import com.clone.twitter.postservice.repository.LikeRepository;
import com.clone.twitter.postservice.validator.LikeValidation;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeRepository likeRepository;
    private final LikeValidation likeValidation;
    private final LikeMapper likeMapper;
    private final UserServiceClient userServiceClient;
    private final UserContext userContext;
    private final LikeEventPublisher likeEventPublisher;

    public LikeDto likePost(LikeDto likeDto) {
        Post checkPost = postService.existsPost(likeDto.getPostId());
        userServiceClient.getUser(likeDto.getUserId());
        likeValidation.verifyUniquenessLikePost(likeDto.getPostId(), likeDto.getUserId());
        Like like = Like.builder()
                .id(likeDto.getUserId())
                .post(checkPost)
                .build();

        LikeEvent likeEvent = buildLikeEvent(likeDto);
        LikeEvent likeEventPost = likeEvent.toBuilder()
                .authorPostId(checkPost.getAuthorId())
                .postId(checkPost.getId())
                .build();

        likeEventPublisher.publish(likeEventPost);

        return likeMapper.toDto(likeRepository.save(like));
    }

    public void deleteLikeFromPost(int postId) {
        UserDto userDto = getUserFromUserService();
        likeRepository.deleteByPostIdAndUserId(postId, userDto.getId());
    }

    private UserDto getUserFromUserService() {
        try {
            return userServiceClient.getUser(userContext.getUserId());
        } catch (FeignException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    public LikeDto likeComment(LikeDto likeDto) {
        Comment checkComment = commentService.findCommentById(likeDto.getCommentId());
        userServiceClient.getUser(likeDto.getUserId());
        likeValidation.verifyUniquenessLikeComment(likeDto.getCommentId(), likeDto.getUserId());
        Like like = Like.builder()
                .id(likeDto.getUserId())
                .comment(checkComment)
                .build();

        LikeEvent likeEvent = buildLikeEvent(likeDto);
        LikeEvent likeEventComment = likeEvent.toBuilder()
                .authorCommentId(checkComment.getAuthorId())
                .commentId(checkComment.getId())
                .build();

        likeEventPublisher.publish(likeEventComment);

        return likeMapper.toDto(likeRepository.save(like));
    }

    public void deleteLikeFromComment(int commentId) {
        UserDto userDto = getUserFromUserService();
        likeRepository.deleteByCommentIdAndUserId(commentId, userDto.getId());
    }

    public LikeEvent buildLikeEvent(LikeDto likeDto) {
        return LikeEvent.builder()
                .authorLikeId(likeDto.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
