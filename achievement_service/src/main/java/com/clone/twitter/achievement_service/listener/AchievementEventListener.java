package com.clone.twitter.achievement_service.listener;

import com.clone.twitter.achievement_service.dto.AchievementEventDto;
import com.clone.twitter.achievement_service.handler.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AchievementEventListener extends AbstractListener<AchievementEventDto> {

    public AchievementEventListener(ObjectMapper objectMapper, List<EventHandler<AchievementEventDto>> eventHandlers) {
        super(objectMapper.registerModule(new JavaTimeModule()), eventHandlers);
    }

    @Override
    protected AchievementEventDto listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), AchievementEventDto.class);
    }
}