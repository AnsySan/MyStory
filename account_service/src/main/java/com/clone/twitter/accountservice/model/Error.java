package com.clone.twitter.accountservice.model;

public class Error extends RuntimeException {
    public Error() {
    }

    public Error(String message) {
        super(message);
    }
}