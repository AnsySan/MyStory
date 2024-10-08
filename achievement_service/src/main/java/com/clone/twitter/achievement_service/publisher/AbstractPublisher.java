package com.clone.twitter.achievement_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractPublisher<T> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper jsonMapper;
    private final String topic;

    public void publish(T eventType) {
        String json;

        try {
            json = jsonMapper.writeValueAsString(eventType);
        } catch (JsonProcessingException e) {
            log.error("Error while converting object to string", e);
            throw new RuntimeException(e);
        }
        redisTemplate.convertAndSend(topic, json);
    }
}