package com.clone.twitter.accountservice.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "thread-pool")
@Data
public class AsyncConfig {
    private int queueCapacity;
    private int maxPoolSize;
    private int corePoolSize;
    private String threadNamePrefix;

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setQueueCapacity(getQueueCapacity());
        executor.setMaxPoolSize(getMaxPoolSize());
        executor.setCorePoolSize(getCorePoolSize());
        executor.setThreadNamePrefix(getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}