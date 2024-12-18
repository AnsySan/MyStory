package com.clone.twitter.userservice.service.event.filter;

import com.clone.twitter.userservice.dto.event.EventFilterDto;
import com.clone.twitter.userservice.model.event.Event;
import com.clone.twitter.userservice.model.event.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EventTypeFilterTest {
    private EventTypeFilter filter;
    private EventFilterDto filterDto;
    private Event event1, event2, event3;

    @BeforeEach
    void init() {
        filter = new EventTypeFilter();
        filterDto = new EventFilterDto();
        event1 = Event.builder().type(EventType.GIVEAWAY).build();
        event2 = Event.builder().type(EventType.POLL).build();
        event3 = Event.builder().type(EventType.GIVEAWAY).build();
    }

    @Test
    void isAcceptableBadDto() {
        assertFalse(filter.isAcceptable(filterDto));
    }

    @Test
    void isAcceptableGoodDto() {
        filterDto.setType(EventType.GIVEAWAY);
        assertTrue(filter.isAcceptable(filterDto));
    }

    @Test
    void apply() {
        filterDto.setType(EventType.GIVEAWAY);
        Event[] expected = new Event[]{event1, event3};
        Stream<Event> out = filter.apply(Stream.of(event1, event2, event3), filterDto);
        assertArrayEquals(expected, out.toArray());
    }
}