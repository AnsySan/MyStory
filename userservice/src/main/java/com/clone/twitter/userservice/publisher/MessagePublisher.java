package com.clone.twitter.userservice.publisher;

public interface MessagePublisher<T> {
    void publish(T event);
}