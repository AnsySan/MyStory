package com.clone.twitter.userservice.service.event.filter;


import com.clone.twitter.userservice.dto.event.EventFilterDto;
import com.clone.twitter.userservice.model.event.Event;

import java.util.stream.Stream;

public interface EventFilter {
    boolean isAcceptable(EventFilterDto filterDto);

    Stream<Event> apply(Stream<Event> events, EventFilterDto filters);
}