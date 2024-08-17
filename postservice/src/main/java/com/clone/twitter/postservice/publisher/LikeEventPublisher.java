package com.clone.twitter.postservice.publisher;

import com.clone.twitter.postservice.event.LikeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class LikeEventPublisher extends AbstractPublisher<LikeEvent> {

    public LikeEventPublisher(RedisTemplate<String, Object> redisTemplate,
                              ObjectMapper objectMapper,
                              @Value("${spring.data.redis.channels.notification_like_channel.name}")
                              String channelName) {
        super(redisTemplate, objectMapper, channelName);
    }
}