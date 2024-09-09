package com.clone.twitter.postservice.service.user;

import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.dto.UserDto;
import com.clone.twitter.postservice.mapper.AuthorMapper;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserServiceClient userServiceClient;
    private final AuthorMapper authorMapper;

    @Override
    @Retryable(retryFor = { FeignException.class }, maxAttempts = 5, backoff = @Backoff(delay = 500, multiplier = 3))
    public UserDto getUserById(Long userId) {

        return userServiceClient.getUser(userId);
    }

    @Override
    @Retryable(retryFor = { FeignException.class }, maxAttempts = 5, backoff = @Backoff(delay = 500, multiplier = 3))
    public AuthorRedisCache getUserAuthorCacheById(Long userId) {

        UserDto userDto = userServiceClient.getUser(userId);
        return authorMapper.toAuthorCache(userDto);
    }
}