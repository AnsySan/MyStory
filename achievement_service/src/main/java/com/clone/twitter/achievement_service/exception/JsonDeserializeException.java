package com.clone.twitter.achievement_service.exception;

public class JsonDeserializeException extends RuntimeException {
    public JsonDeserializeException(String message) {
        super(message);
    }
}