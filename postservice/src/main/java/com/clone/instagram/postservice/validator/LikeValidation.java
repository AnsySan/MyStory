package com.clone.instagram.postservice.validator;

import com.clone.instagram.postservice.exception.DataValidationException;
import com.clone.instagram.postservice.repository.LikeRepository;
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
}