package com.clone.twitter.postservice.redis.cache.service.author;

import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;

import java.util.concurrent.CompletableFuture;

public interface AuthorRedisCacheService {

    public CompletableFuture<AuthorRedisCache> save(AuthorRedisCache entity);
}
