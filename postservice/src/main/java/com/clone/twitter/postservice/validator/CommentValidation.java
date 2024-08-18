package com.clone.twitter.postservice.validator;

import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.dto.UserDto;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.repository.CommentRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidation {
    private final UserServiceClient userServiceClient;
    private final CommentRepository commentRepository;

    public void authorExistenceValidation(Integer userId) {
        try {
            UserDto user = userServiceClient.getUser(userId);
        } catch (FeignException e) {
            throw new EntityNotFoundException("User with id " + userId + " is not found");
        }
    }

    public void validateCommentExistence(Integer commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new DataValidationException("No comment with id" + commentId + " found");
        }
    }

}