package com.clone.twitter.postservice.validator.comment;

import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.repository.comment.CommentRepository;
import com.clone.twitter.postservice.validator.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentValidatorImpl implements CommentValidator {
    private final UserValidator userValidator;
    private final CommentRepository commentRepository;

    @Override
    public void validateCreateComment(long userId) {
        userValidator.validateUserExistence(userId);
    }
    @Override
    public void validateUpdateAlbum(Comment commentToUpdate, long userId) {
        userValidator.validateUserExistence(userId);
        validateAuthorIsCorrect(commentToUpdate, userId, "update");
    }
    @Override
    public void validateDeleteAlbum(long postId, long userId, Comment comment) {
        userValidator.validateUserExistence(userId);
        validatePostHasThisComment(postId, comment);
    }

    private void validateAuthorIsCorrect(Comment commentToUpdate, long userId, String errorDescription) {
        if (commentToUpdate.getAuthorId() != userId) {
            throw new DataValidationException(String.format("Only author can %s comment", errorDescription));
        }
    }

    private void validatePostHasThisComment(long postId, Comment comment) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        if (!commentList.contains(comment)) {
            throw new DataValidationException(String.format("Post %d don't have comment %d", postId, comment.getId()));
        }
    }
}