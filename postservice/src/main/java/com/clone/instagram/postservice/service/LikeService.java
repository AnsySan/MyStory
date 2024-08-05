package com.clone.instagram.postservice.service;

import com.clone.instagram.postservice.client.UserServiceClient;
import com.clone.instagram.postservice.context.UserContext;
import com.clone.instagram.postservice.dto.LikeDto;
import com.clone.instagram.postservice.dto.UserDto;
import com.clone.instagram.postservice.entity.Like;
import com.clone.instagram.postservice.entity.Post;
import com.clone.instagram.postservice.event.LikeEvent;
import com.clone.instagram.postservice.mapper.LikeMapper;
import com.clone.instagram.postservice.publisher.LikeEventPublisher;
import com.clone.instagram.postservice.repository.LikeRepository;
import com.clone.instagram.postservice.validator.LikeValidation;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final PostService postService;
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

    public void deleteLikeFromPost(Long postId) {
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

    public LikeEvent buildLikeEvent(LikeDto likeDto) {
        return LikeEvent.builder()
                .authorLikeId(likeDto.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
