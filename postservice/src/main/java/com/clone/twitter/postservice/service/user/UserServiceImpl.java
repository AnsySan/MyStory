package com.clone.twitter.postservice.service.user;

import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.dto.user.UserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServiceClient userServiceClient;

    @Override
    @Retryable(retryFor = { FeignException.class }, maxAttempts = 5, backoff = @Backoff(delay = 500, multiplier = 3))
    public UserDto getUserById(long userId) {

        return userServiceClient.getUser(userId);
    }

    @Override
    @Retryable(retryFor = { FeignException.class }, maxAttempts = 5, backoff = @Backoff(delay = 500, multiplier = 3))
    public List<UserDto> getAllUsers() {

        return userServiceClient.getAllUsers();
    }
}