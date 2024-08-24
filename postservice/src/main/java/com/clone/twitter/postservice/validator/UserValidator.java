package com.clone.twitter.postservice.validator;

import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.dto.UserDto;
import com.clone.twitter.postservice.exception.NotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserValidator {
    private final UserServiceClient userServiceClient;

    public void validateUserExistence(int userId) {
        try {
            log.debug("Fetching user with ID: " + userId);
            UserDto userDto = userServiceClient.getUser(userId);
            log.info("Found user: {}", userDto);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException(String.format("User with id '%d' not exist", userId));
        } catch (FeignException e) {
            throw new RuntimeException("Error fetching user", e);
        }
    }
}