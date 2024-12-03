package com.clone.twitter.payment_service.scheduling;

import com.clone.twitter.payment_service.entity.Payment;
import com.clone.twitter.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentScheduler {

    private final PaymentService service;
    private final ThreadPoolTaskScheduler paymentTaskScheduler;

    @Async
    @Scheduled(cron = "${scheduling.gather-payment}")
    public void getScheduledPayment() {
        List<Payment> payments = service.getScheduledPayment();
        log.info("Got scheduled payments: {}", payments);

        for (Payment payment : payments) {
            paymentTaskScheduler.schedule(() ->
                            service.clear(payment),
                    payment.getScheduledAt().toInstant(ZoneOffset.UTC));
            log.info("Payment scheduled: {}", payment);
        }
    }
}