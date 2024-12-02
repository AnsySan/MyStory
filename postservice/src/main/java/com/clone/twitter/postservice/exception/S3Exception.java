package com.clone.twitter.postservice.exception;

public class S3Exception extends RuntimeException {
    public S3Exception(String message) {
        super(message);
    }
}