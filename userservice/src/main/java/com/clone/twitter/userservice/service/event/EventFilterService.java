package com.clone.twitter.userservice.service.event;


import com.clone.twitter.userservice.dto.event.EventFilterDto;
import com.clone.twitter.userservice.model.event.Event;

import java.util.stream.Stream;

public interface EventFilterService {
    Stream<Event> apply(Stream<Event> events, EventFilterDto filterDto);
}