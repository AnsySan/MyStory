package com.clone.twitter.postservice.validator;

import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeValidation {
    private final LikeRepository likeRepository;

    public void verifyUniquenessLikePost(long postId, long userId) {
        if (likeRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            throw new DataValidationException("The user has already liked this post");
        }
    }

    public void verifyUniquenessLikeComment(long commentId, long userId) {
        if (likeRepository.findByCommentIdAndUserId(commentId, userId).isPresent()) {
            throw new DataValidationException("The user has already liked this comment");
        }
    }
}