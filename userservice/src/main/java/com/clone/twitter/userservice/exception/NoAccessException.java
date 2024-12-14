package com.clone.twitter.userservice.exception;

public class NoAccessException extends RuntimeException{
    public NoAccessException(String message) {
        super(message);
    }
}