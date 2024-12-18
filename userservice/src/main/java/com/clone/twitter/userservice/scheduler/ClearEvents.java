package com.clone.twitter.userservice.scheduler;

import com.clone.twitter.userservice.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearEvents {
    private final EventService eventService;

    @Scheduled(cron = "${scheduler.clearEvents.cronExpression}")
    public void clearEvents() {
        eventService.clearEvents();
    }
}