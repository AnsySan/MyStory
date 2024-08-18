package com.clone.twitter.postservice.publisher.publishers;

import com.clone.twitter.postservice.event.CommentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommentEventPublisher extends MessagePublisher<CommentEvent> {

    public CommentEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                 ObjectMapper jsonMapper,
                                 @Value("${spring.data.redis.channels.comment_channel.name}") String commentTopic) {
        super(redisTemplate, jsonMapper, commentTopic);

    }
}