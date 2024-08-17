package com.clone.twitter.postservice.validation;

import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Like;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.repository.LikeRepository;
import com.clone.twitter.postservice.validator.LikeValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeValidationTest {
    @Mock
    private LikeRepository likeRepository;
    @InjectMocks
    private LikeValidation likeValidation;

    private final int userId = 1;
    private final int postId = 2;
    private final int commentId = 3;

    private Like like;

    @BeforeEach
    void setUp() {
        Post post = Post.builder()
                .id(postId)
                .build();

        Comment comment = Comment.builder()
                .id(3)
                .build();

        like = Like.builder()
                .userId(1)
                .post(post)
                .comment(comment)
                .build();
    }

    @Test
    @DisplayName("Positive scenario: The user did not put two likes on the same post")
    public void testVerifyUniquenessLikePost_shouldNotThrowException() {
        Optional<Like> optionalLike = Optional.empty();
        when(likeRepository.findByPostIdAndUserId(postId, userId)).thenReturn(optionalLike);

        likeValidation.verifyUniquenessLikePost(postId, userId);

        verify(likeRepository, times(1)).findByPostIdAndUserId(postId, userId);
    }

    @Test
    @DisplayName("Negative scenario: The user put two likes under one post")
    public void testVerifyUniquenessLikePost_shouldThrowException() {
        Optional<Like> optionalLike  = Optional.of(like);
        when(likeRepository.findByPostIdAndUserId(postId, userId)).thenReturn(optionalLike);

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> likeValidation.verifyUniquenessLikePost(postId, userId));

        assertEquals("The user has already liked this post", exception.getMessage());
        verify(likeRepository, times(1)).findByPostIdAndUserId(postId, userId);
    }

    @Test
    @DisplayName("Positive scenario: The user did not put two likes on the same comment")
    public void testVerifyUniquenessLikeComment_shouldNotThrowException() {
        Optional<Like> optionalLike = Optional.empty();
        when(likeRepository.findByCommentIdAndUserId(commentId, userId)).thenReturn(optionalLike);

        likeValidation.verifyUniquenessLikeComment(commentId, userId);

        verify(likeRepository, times(1)).findByCommentIdAndUserId(commentId, userId);
    }

    @Test
    @DisplayName("Negative scenario: The user put two likes under one comment")
    public void testVerifyUniquenessLikeComment_shouldThrowException() {
        Optional<Like> optionalLike  = Optional.of(like);
        when(likeRepository.findByCommentIdAndUserId(commentId, userId)).thenReturn(optionalLike);

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> likeValidation.verifyUniquenessLikeComment(commentId, userId));

        assertEquals("The user has already liked this comment", exception.getMessage());
        verify(likeRepository, times(1)).findByCommentIdAndUserId(commentId, userId);
    }
}