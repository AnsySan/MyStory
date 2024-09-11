package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    public UserDto getUser(@PathVariable long userId) {
        log.info("getUser method was called");
        return userServiceImpl.getUser(userId);
    }


    @Operation(summary = "Create user")
    @PostMapping("creature")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return userServiceImpl.create( userDto );
    }

    @PostMapping
    @Operation(summary = "Get users by ids")
    public List<UserDto> getUsersByIds(@RequestBody List<Long> ids) {
        log.info("getUsersByIds was called");
        return userServiceImpl.getUsersByIds(ids);
    }

    @PostMapping("/deactivate/{userId}")
    @Operation(summary = "Deactivate user")
    public void deactivate(@PathVariable long userId) {
        userServiceImpl.deactivate(userId);
    }
}