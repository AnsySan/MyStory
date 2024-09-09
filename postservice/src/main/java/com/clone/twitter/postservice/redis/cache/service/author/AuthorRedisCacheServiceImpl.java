package com.clone.twitter.postservice.redis.cache.service.author;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import com.clone.twitter.postservice.redis.cache.repository.AuthorRedisRepository;
import com.clone.twitter.postservice.redis.cache.service.RedisOperations;
import com.clone.twitter.postservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Async("authorsCacheTaskExecutor")
public class AuthorRedisCacheServiceImpl implements AuthorRedisCacheService {

    private final AuthorRedisRepository authorRedisRepository;
    private final RedisOperations redisOperations;
    private final UserServiceImpl userServiceImpl;
    private final UserContext userContext;

    @Override
    public CompletableFuture<AuthorRedisCache> save(AuthorRedisCache entity) {

        userContext.setUserId(entity.getId());

        AuthorRedisCache redisUser = userServiceImpl.getUserAuthorCacheById(entity.getId());

        entity = redisOperations.updateOrSave(authorRedisRepository, redisUser, redisUser.getId());

        log.info("Saved author with id {} to cache: {}", entity.getId(), redisUser);

        return CompletableFuture.completedFuture(redisUser);
    }
}