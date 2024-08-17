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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @Mock
    private PostService postService;
    @Mock
    private CommentService commentService;
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
    private Comment comment;
    private UserDto userDto;
    private final int userId = 1;

    @BeforeEach
    public void setUp() {
        likeDto = LikeDto.builder()
                .id(1)
                .userId(userId)
                .postId(3)
                .commentId(4)
                .build();

        userDto = UserDto.builder()
                .id(userId)
                .build();

        post = Post.builder()
                .id(3)
                .build();

        comment = Comment.builder()
                .id(4)
                .build();

        like = Like.builder()
                .id(1)
                .userId(userId)
                .post(post)
                .comment(comment)
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

    @Test
    @DisplayName("Like the comment")
    public void testLikeComment() {
        when(commentService.findCommentById(likeDto.getCommentId())).thenReturn(comment);
        when(userServiceClient.getUser(likeDto.getUserId())).thenReturn(userDto);
        when(likeRepository.save(any(Like.class))).thenReturn(like);
        when(likeMapper.toDto(like)).thenReturn(likeDto);

        assertEquals(likeDto, likeService.likeComment(likeDto));

        verify(likeEventPublisher, times(1)).publish(any(LikeEvent.class));
        verify(likeValidation, times(1)).verifyUniquenessLikeComment(likeDto.getCommentId(), likeDto.getUserId());
    }

    @Test
    @DisplayName("Remove like from comment")
    public void testDeleteLikeFromComment() {
        when(userServiceClient.getUser(anyInt())).thenReturn(userDto);

        likeService.deleteLikeFromComment(post.getId());

        verify(userServiceClient, times(1)).getUser(anyInt());
        verify(likeRepository, times(1)).deleteByCommentIdAndUserId(post.getId(), userId);
    }
}