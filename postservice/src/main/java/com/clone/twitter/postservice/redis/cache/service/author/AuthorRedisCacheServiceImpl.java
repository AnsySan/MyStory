package com.clone.twitter.postservice.redis.cache.service.author;

import com.clone.twitter.postservice.config.context.UserContext;
import com.clone.twitter.postservice.dto.user.UserDto;
import com.clone.twitter.postservice.mapper.AuthorMapper;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import com.clone.twitter.postservice.redis.cache.repository.AuthorRedisRepository;
import com.clone.twitter.postservice.redis.cache.service.RedisOperations;
import com.clone.twitter.postservice.service.user.UserService;
import com.clone.twitter.postservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserService userService;
    private final UserContext userContext;
    private final AuthorMapper authorMapper;

    @Override
    public CompletableFuture<UserDto> save(AuthorRedisCache entity) {

        userContext.setUserId(entity.getId());

        UserDto userDto = userService.getUserById(entity.getId());
        AuthorRedisCache redisUser = authorMapper.toCache(userDto);

        entity = redisOperations.updateOrSave(authorRedisRepository, redisUser, redisUser.getId());

        log.info("Saved author with id {} to cache: {}", entity.getId(), redisUser);

        return CompletableFuture.completedFuture(userDto);
    }

    @Override
    public CompletableFuture<UserDto> getUserDtoByCache(AuthorRedisCache entity) {

        userContext.setUserId(entity.getId());

        UserDto userDto = userService.getUserById(entity.getId());

        return CompletableFuture.completedFuture(userDto);
    }
}