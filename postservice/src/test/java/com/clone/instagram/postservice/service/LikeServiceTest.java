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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @Mock
    private PostService postService;
    @Mock
    private LikeValidation likeValidation;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private UserContext userContext;
    @Mock
    private LikeEventPublisher likeEventPublisher;
    @Spy
    private LikeMapper likeMapper;

    @InjectMocks
    private LikeService likeService;

    private Like like;
    private LikeDto likeDto;
    private Post post;
    private UserDto userDto;
    private final int userId = 1;

    @BeforeEach
    public void setUp() {
        likeDto = LikeDto.builder()
                .id(1)
                .userId(userId)
                .postId(3)
                .build();

        userDto = UserDto.builder()
                .id(userId)
                .build();

        post = Post.builder()
                .id(3)
                .build();

        like = Like.builder()
                .id(1)
                .userId(userId)
                .post(post)
                .build();
    }

    @Test
    @DisplayName("Like the post")
    public void testLikePost() {
        when(postService.existsPost(likeDto.getPostId())).thenReturn(post);
        when(userServiceClient.getUser(likeDto.getUserId())).thenReturn(userDto);
        when(likeRepository.save(any(Like.class))).thenReturn(like);
        when(likeMapper.toDto(like)).thenReturn(likeDto);

        assertEquals(likeDto, likeService.likePost(likeDto));

        verify(likeEventPublisher, times(1)).publish(any(LikeEvent.class));
        verify(likeValidation, times(1)).verifyUniquenessLikePost(likeDto.getPostId(), likeDto.getUserId());
    }
    @Test
    @DisplayName("Remove like from post")
    public void testDeleteLikeFromPost() {
        when(userServiceClient.getUser(anyInt())).thenReturn(userDto);

        likeService.deleteLikeFromPost(post.getId());

        verify(userServiceClient, times(1)).getUser(anyInt());
        verify(likeRepository, times(1)).deleteByPostIdAndUserId(post.getId(), userId);
    }

}