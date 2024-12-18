package com.clone.twitter.userservice.service.event;


import com.clone.twitter.userservice.dto.event.EventDto;
import com.clone.twitter.userservice.dto.event.EventFilterDto;
import com.clone.twitter.userservice.model.event.Event;

import java.util.List;

public interface EventService {

    List<EventDto> getParticipatedEvents(long userId);

    List<EventDto> getOwnedEvents(long userId);

    EventDto updateEvent(EventDto event);

    EventDto deleteEvent(long eventId);

    List<EventDto> getEventsByFilter(EventFilterDto filter);

    EventDto getEvent(long eventId);

    EventDto create(EventDto event);

    void deleteAll(List<Event> events);

    void clearEvents();
}