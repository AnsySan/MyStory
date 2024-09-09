package com.clone.twitter.postservice.kafka.producer;

import com.clone.twitter.postservice.kafka.event.KafkaEvent;

public interface KafkaProducer<T extends KafkaEvent> {
    void produce(T event);

    void produce(T event, Runnable runnable);
}