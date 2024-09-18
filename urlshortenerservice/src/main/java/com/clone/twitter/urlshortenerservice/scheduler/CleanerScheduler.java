package com.clone.twitter.urlshortenerservice.scheduler;

import com.clone.twitter.urlshortenerservice.service.hash.HashServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CleanerScheduler {

    private final HashServiceImpl hashServiceImpl;

    @Scheduled(cron = "${app.scheduler.cleaner}")
    public void cleanAndMoveHashes() {
        hashServiceImpl.clean();
    }

}