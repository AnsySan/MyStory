package com.clone.twitter.achievement_service.listener;

import com.clone.twitter.achievement_service.dto.LikeEvent;
import com.clone.twitter.achievement_service.handler.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LikeEventListener extends AbstractListener<LikeEvent> {

    public LikeEventListener(ObjectMapper objectMapper,
                             List<EventHandler<LikeEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected LikeEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), LikeEvent.class);
    }
}