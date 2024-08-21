package com.clone.twitter.userservice.publisher;

import com.clone.twitter.userservice.dto.SearchAppearanceEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchAppearanceEventPublisher extends AbstractPublisher<SearchAppearanceEvent> {

    public SearchAppearanceEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                          ObjectMapper objectMapper,
                                          @Value("${spring.data.channels.profile_search_channel.name}")
                                          String channelName) {
        super(redisTemplate, objectMapper, channelName);
    }
}