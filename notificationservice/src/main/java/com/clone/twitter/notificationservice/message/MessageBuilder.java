package com.clone.twitter.notificationservice.message;

import java.util.Locale;

public interface MessageBuilder<T> {
    Class<T> getEventType();

    String buildMessage(T event, Locale locale);
}