package com.clone.twitter.postservice.kafka.consumer.comment;

import com.clone.twitter.postservice.kafka.consumer.KafkaConsumer;
import com.clone.twitter.postservice.kafka.event.comment.CommentKafkaEvent;
import com.clone.twitter.postservice.mapper.CommentMapper;
import com.clone.twitter.postservice.redis.cache.service.comment.CommentRedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentConsumer implements KafkaConsumer<CommentKafkaEvent> {

    private final CommentMapper commentMapper;
    private final CommentRedisCacheService commentRedisCacheService;

    @Override
    @KafkaListener(topics = "${spring.data.kafka.topics.topic-settings.comments.name}", groupId = "${spring.data.kafka.group-id}")
    public void consume(@Payload CommentKafkaEvent event, Acknowledgment ack) {

        log.info("Received new comment event {}", event);

        switch (event.getState()) {
            case ADD, UPDATE -> commentRedisCacheService.save(commentMapper.toRedisCache(event));
            case DELETE -> commentRedisCacheService.deleteById(event.getId());
        }

        ack.acknowledge();
    }
}