package com.clone.twitter.payment_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolTaskSchedulerConfig {

    @Value("${scheduling.payment-scheduler-pool-size}")
    private int poolSize;

    @Value("${scheduling.payment-scheduler-thread-name-prefix}")
    private String THREAD_NAME_PREFIX;

    @Bean
    public ThreadPoolTaskScheduler paymentTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix(THREAD_NAME_PREFIX);

        return scheduler;
    }
}