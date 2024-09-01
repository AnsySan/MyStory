package com.clone.twitter.postservice.config.redis;

import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;
import com.clone.twitter.postservice.redis.cache.entity.FeedRedisCache;
import com.clone.twitter.postservice.redis.cache.entity.PostRedisCache;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RedisCacheConfig {

    @Value("${spring.data.redis.cache.default-ttl}")
    private Long TTl;

    @Value("${spring.data.redis.cache.authors.name}")
    private String authorsName;

    @Value("${spring.data.redis.cache.comments.name}")
    private String commentsName;

    @Value("${spring.data.redis.cache.feed.name}")
    private String feedName;

    @Value("${spring.data.redis.cache.posts.name}")
    private String postsName;

    @Value("${spring.data.redis.cache.users.name}")
    private String usersName;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(TTl, ChronoUnit.SECONDS));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    @SuppressWarnings("unused")
    public class RedisKeyspaceConfiguration extends KeyspaceConfiguration {

        private static final List<Class<?>> entityClasses = List.of(
                PostRedisCache.class,
                AuthorRedisCache.class,
                CommentRedisCache.class,
                FeedRedisCache.class
        );

        @Override
        protected @NonNull Iterable<KeyspaceSettings> initialConfiguration() {

            return entityClasses.stream().map(this::getKeyspaceSettings).toList();
        }

        @Override
        public @NonNull KeyspaceSettings getKeyspaceSettings(@NonNull Class<?> type) {

            String cacheName = type.getAnnotation(RedisHash.class).value();

            KeyspaceSettings keyspaceSettings = new KeyspaceSettings(type, cacheName);
            keyspaceSettings.setTimeToLive(TTl);

            return keyspaceSettings;
        }
    }
}