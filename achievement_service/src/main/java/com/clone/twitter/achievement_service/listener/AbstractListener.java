package com.clone.twitter.achievement_service.listener;

import com.clone.twitter.achievement_service.dto.Event;
import com.clone.twitter.achievement_service.handler.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractListener<T extends Event> implements MessageListener {

    protected final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;

    protected abstract T listenEvent(Message message) throws IOException;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        T event;
        try {
            event = listenEvent(message);
        } catch (IOException e) {
            log.warn("Unsuccessful mapping", e);
            throw new RuntimeException(e);
        }


        log.info(eventHandlers.toString());
        eventHandlers.forEach(handler -> handler.handleEvent(event));
        log.info("Data successfully passed to AchievementService!");
    }
}