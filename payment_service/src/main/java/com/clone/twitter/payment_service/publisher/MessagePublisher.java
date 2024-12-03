package com.clone.twitter.payment_service.publisher;

public interface MessagePublisher<E> {
    void publish(E event);
}