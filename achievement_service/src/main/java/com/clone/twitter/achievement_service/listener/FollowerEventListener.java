package com.clone.twitter.achievement_service.listener;

import com.clone.twitter.achievement_service.dto.FollowerEvent;
import com.clone.twitter.achievement_service.handler.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FollowerEventListener extends AbstractListener<FollowerEvent> {

    public FollowerEventListener(ObjectMapper objectMapper,
                                 List<EventHandler<FollowerEvent>> eventHandlers) {
        super(objectMapper.registerModule(new JavaTimeModule()), eventHandlers);
    }

    @Override
    protected FollowerEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), FollowerEvent.class);
    }
}