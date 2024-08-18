package com.clone.twitter.postservice.publisher;

public interface MessagePublisher {
    <T>  void publish(T event);
}