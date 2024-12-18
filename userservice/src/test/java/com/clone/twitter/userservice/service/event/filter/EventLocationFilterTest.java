package com.clone.twitter.userservice.service.event.filter;

import com.clone.twitter.userservice.dto.event.EventFilterDto;
import com.clone.twitter.userservice.model.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventLocationFilterTest {
    private EventLocationFilter filter;
    private EventFilterDto filterDto;
    private Event event1, event2, event3;

    @BeforeEach
    void init() {
        filter = new EventLocationFilter();
        filterDto = new EventFilterDto();
        event1 = Event.builder().location("loc1").build();
        event2 = Event.builder().location("loc2").build();
        event3 = Event.builder().location("loc1").build();
    }

    @Test
    void isAcceptableBadDto() {
        assertFalse(filter.isAcceptable(filterDto));
    }

    @Test
    void isAcceptableGoodDto() {
        filterDto.setLocation("loc1");
        assertTrue(filter.isAcceptable(filterDto));
    }

    @Test
    void apply() {
        filterDto.setLocation("loc1");
        Event[] expected = new Event[]{event1, event3};
        Stream<Event> out = filter.apply(Stream.of(event1, event2, event3), filterDto);
        assertArrayEquals(expected, out.toArray());
    }
}