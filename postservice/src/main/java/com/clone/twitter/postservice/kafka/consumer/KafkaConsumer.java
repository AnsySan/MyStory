package com.clone.twitter.postservice.kafka.consumer;

import com.clone.twitter.postservice.kafka.event.KafkaEvent;
import org.springframework.kafka.support.Acknowledgment;

public interface KafkaConsumer<T extends KafkaEvent> {

    void consume(T event, Acknowledgment ack);
}