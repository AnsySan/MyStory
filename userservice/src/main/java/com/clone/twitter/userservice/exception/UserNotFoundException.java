package com.clone.twitter.userservice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(MessageError error) { //TODO EntityNotFoundException назвать
        super(error.getMessage());

    }
}