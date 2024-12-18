package com.clone.twitter.userservice.service.event;

import com.clone.twitter.userservice.dto.event.EventDto;
import com.clone.twitter.userservice.dto.event.EventFilterDto;
import com.clone.twitter.userservice.exception.EntityNotFoundException;
import com.clone.twitter.userservice.mapper.event.EventMapper;
import com.clone.twitter.userservice.model.event.Event;
import com.clone.twitter.userservice.repository.event.EventRepository;
import com.clone.twitter.userservice.validator.event.EventValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventValidator validator;
    private final EventRepository eventRepository;
    private final EventFilterService eventFilterService;
    private final EventMapper mapper;
    @Value("${batchSize.eventDeletion}")
    private int batchSize;

    @Override
    @Transactional
    public List<EventDto> getParticipatedEvents(long userId) {
        validator.validateUserId(userId);

        return eventRepository.findParticipatedEventsByUserId(userId).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<EventDto> getOwnedEvents(long userId) {
        validator.validateUserId(userId);

        return eventRepository.findAllByUserId(userId).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventDto updateEvent(@NonNull EventDto event) {
        validator.validate(event);
        validator.validateOwnersRequiredSkills(event);

        Event eventEntity = mapper.toEntity(event);
        Event saved = eventRepository.save(eventEntity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public EventDto deleteEvent(long eventId) {
        Event eventToDelete = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("event with id=" + eventId + " not found"));
        eventRepository.deleteById(eventToDelete.getId());
        return mapper.toDto(eventToDelete);
    }

    @Override
    @Transactional
    public List<EventDto> getEventsByFilter(@NonNull EventFilterDto filters) {
        Stream<Event> events = eventRepository.findAll().stream();

        return eventFilterService.apply(events, filters)
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventDto getEvent(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("event with id=" + eventId + " not found"));
        return mapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto create(@NonNull EventDto event) {
        validator.validate(event);
        validator.validateOwnersRequiredSkills(event);

        Event eventEntity = mapper.toEntity(event);
        Event saved = eventRepository.save(eventEntity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteAll(List<Event> events) {
        eventRepository.deleteAll(events);
    }

    @Scheduled(cron = "${scheduler.clearEvents.cronExpression}")
    public void clearEvents() {
        List<Long> ids = eventRepository.findCompletedOrCanceledEventIds();

        if (ids.isEmpty()) {
            return;
        }

        List<List<Long>> partitions = ListUtils.partition(ids, batchSize);
        for (List<Long> partition : partitions) {
            clearEventsAsync(partition);
        }
    }

    @Async("threadPoolForEventProcessing")
    void clearEventsAsync(List<Long> partition) {
        eventRepository.deleteAllById(partition);
    }
}