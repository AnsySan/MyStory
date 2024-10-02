package com.clone.twitter.payment_service.exception;

public class NoSuchCurrencyException extends RuntimeException {
    public NoSuchCurrencyException(String message) {
        super(message);
    }
}