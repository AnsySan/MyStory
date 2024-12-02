package com.clone.twitter.postservice.config.executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService executorService(@Value("${post.moderator.threads-count}") int threadsCount) {
        return Executors.newFixedThreadPool(threadsCount);
    }
}