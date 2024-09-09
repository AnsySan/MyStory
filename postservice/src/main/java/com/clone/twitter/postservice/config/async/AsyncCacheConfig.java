package com.clone.twitter.postservice.config.async;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class AsyncCacheConfig {

    @Value("${async.redis.corePoolSize}")
    private int corePoolSize;

    @Value("${async.redis.maxPoolSize}")
    private int maxPoolSize;

    @Value("${async.redis.queueCapacity}")
    private int queueCapacity;

    @Bean
    public Executor postsCacheTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("PostCacheAsyncThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor commentsCacheTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("CommentsCacheAsyncThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor authorsCacheTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("AuthorsCacheAsyncThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor feedCacheTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("FeedCacheAsyncThread-");
        executor.initialize();
        return executor;
    }
}