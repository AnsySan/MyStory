package com.clone.twitter.postservice.kafka.consumer.like;

import com.clone.twitter.postservice.kafka.consumer.KafkaConsumer;
import com.clone.twitter.postservice.kafka.event.like.PostLikeKafkaEvent;
import com.clone.twitter.postservice.redis.cache.service.post.PostRedisCacheServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostLikeConsumer implements KafkaConsumer<PostLikeKafkaEvent> {

    private final PostRedisCacheServiceImpl commentRedisCacheService;

    @Override
    @KafkaListener(topics = "${spring.data.kafka.topics.topic-settings.post-likes.name}", groupId = "${spring.data.kafka.group-id}")
    public void consume(@Payload PostLikeKafkaEvent event, Acknowledgment ack) {

        log.info("Received new post like event {}", event);

        switch (event.getState()) {
            case ADD -> commentRedisCacheService.incrementLikes(event.getPostId());
            case DELETE -> commentRedisCacheService.decrementLikes(event.getPostId());
        }

        ack.acknowledge();
    }
}