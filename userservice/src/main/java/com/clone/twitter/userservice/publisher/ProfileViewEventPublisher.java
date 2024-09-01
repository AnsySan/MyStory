package com.clone.twitter.userservice.publisher;

import com.clone.twitter.userservice.dto.profile.ProfileViewEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileViewEventPublisher extends AbstractPublisher<ProfileViewEventDto> {
    public ProfileViewEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                     ObjectMapper jsonMapper,
                                     @Value("${spring.data.channels.profile_view_channel.name}")  String profileViewTopic) {
        super(redisTemplate, jsonMapper, profileViewTopic);
    }
}