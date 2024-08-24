package com.clone.twitter.achievement_service.listener;

import com.clone.twitter.achievement_service.dto.CommentEvent;
import com.clone.twitter.achievement_service.handler.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CommentEventListener extends AbstractListener<CommentEvent> {

    public CommentEventListener(List<EventHandler<CommentEvent>> eventHandlers, ObjectMapper objectMapper) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected CommentEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), CommentEvent.class);
    }
}