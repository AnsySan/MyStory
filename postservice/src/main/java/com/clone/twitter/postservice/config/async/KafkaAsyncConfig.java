package com.clone.twitter.postservice.config.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class KafkaAsyncConfig {

    @Value("${async.kafka.corePoolSize}")
    private int corePoolSize;

    @Value("${async.kafka.maxPoolSize}")
    private int maxPoolSize;

    @Value("${async.kafka.queueCapacity}")
    private int queueCapacity;

    @Bean
    public Executor kafkaThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("KafkaAsyncThread-");
        executor.initialize();
        return executor;
    }
}