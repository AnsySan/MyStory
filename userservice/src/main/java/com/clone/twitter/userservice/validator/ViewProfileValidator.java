package com.clone.twitter.userservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewProfileValidator {
    private final UserValidator userValidator;

    public void validate(long authorId, long receiverId) {
        userValidator.validateUserExistence(authorId);
        userValidator.validateUserExistence(receiverId);
    }
}