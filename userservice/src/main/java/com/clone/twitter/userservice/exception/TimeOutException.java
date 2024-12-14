package com.clone.twitter.userservice.exception;

public class TimeOutException extends RuntimeException {
    public TimeOutException(String message) {
        super(message);
    }
}