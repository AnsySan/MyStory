package com.clone.twitter.postservice.redis.publisher;

import com.clone.twitter.postservice.redis.publisher.event.RedisEvent;

public interface RedisPublisher<T extends RedisEvent> {
    void publish(T event);
}