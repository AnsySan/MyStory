package com.clone.twitter.postservice;

import com.clone.twitter.postservice.config.redis.RedisCacheConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
@EnableKafka
@ConfigurationPropertiesScan(basePackages = "com.clone.twitter.postservice.property")
@EnableRedisRepositories(
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP,
        keyspaceConfiguration = RedisCacheConfig.RedisKeyspaceConfiguration.class,
        shadowCopy = RedisKeyValueAdapter.ShadowCopy.OFF)
@EnableFeignClients(basePackages = "com.clone.twitter.postservice.client")
@EnableRetry
@OpenAPIDefinition(
        info = @Info(
                title = "User Service",
                version = "1.0.0")
)
public class PostserviceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PostserviceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}