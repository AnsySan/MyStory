package com.clone.twitter.achievement_service.handler;


import com.clone.twitter.achievement_service.dto.Event;

public interface EventHandler<T extends Event> {
    void handleEvent(T event);
}