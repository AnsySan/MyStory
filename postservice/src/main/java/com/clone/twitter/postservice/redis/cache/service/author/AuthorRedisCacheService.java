package com.clone.twitter.postservice.redis.cache.service.author;

import com.clone.twitter.postservice.dto.user.UserDto;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;

import java.util.concurrent.CompletableFuture;

public interface AuthorRedisCacheService {

    CompletableFuture<UserDto> save(AuthorRedisCache entity);

    CompletableFuture<UserDto> getUserDtoByCache(AuthorRedisCache entity);
}