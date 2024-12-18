package com.clone.twitter.userservice.scheduler;

import com.clone.twitter.userservice.service.user.premium.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledPremiumStatusRemover {

    private final PremiumService premiumService;

    @Async
    @Scheduled(cron = "${premium.scheduler.expired-premium-remover.cron}")
    public void removeExpiredPremiumStatus() {
        premiumService.deleteAllExpiredPremium();
    }
}