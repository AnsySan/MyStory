package com.clone.twitter.postservice.validator.comment;

import com.clone.twitter.postservice.validator.user.UserValidatorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidatorImpl implements CommentValidator {

    private final UserValidatorImpl userValidatorImpl;

    @Override
    public void validateCreateComment(long userId) {
        userValidatorImpl.validateUserExistence(userId);
    }
}