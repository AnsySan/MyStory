package com.clone.twitter.payment_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@RequiredArgsConstructor
@Slf4j
public abstract class EventPublisher<E> implements MessagePublisher<E> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;
    private final ObjectMapper mapper;

    public void publish(E event) {
        String json = getJson(event);
        redisTemplate.convertAndSend(topic.getTopic(), json);
        log.info("Published event: {}", json);
    }

    private String getJson(E event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize event", e);
        }
    }
}