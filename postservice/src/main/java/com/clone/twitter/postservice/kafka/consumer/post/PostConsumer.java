package com.clone.twitter.postservice.kafka.consumer.post;

import com.clone.twitter.postservice.kafka.consumer.KafkaConsumer;
import com.clone.twitter.postservice.kafka.event.post.PostKafkaEvent;
import com.clone.twitter.postservice.mapper.post.PostMapper;
import com.clone.twitter.postservice.redis.cache.service.post.PostRedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostConsumer implements KafkaConsumer<PostKafkaEvent> {

    private final PostMapper postMapper;
    private final PostRedisCacheService postCacheService;

    @Override
    @KafkaListener(topics = "${spring.data.kafka.topics.topic-settings.posts.name}", groupId = "${spring.data.kafka.group-id}")
    public void consume(@Payload PostKafkaEvent event, Acknowledgment ack) {

        log.info("Received new post event {}", event);

        switch (event.getState()) {
            case ADD, UPDATE -> postCacheService.save(postMapper.toRedisCache(event));
            case DELETE -> postCacheService.deleteById(event.getPostId());
        }

        ack.acknowledge();
    }
}