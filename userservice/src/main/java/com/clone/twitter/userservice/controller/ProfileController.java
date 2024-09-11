package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.service.profile.ProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProfileController {
    private final ProfileServiceImpl profileServiceImpl;

    @GetMapping("/view/{userId}")
    @Operation(summary = "Add profile view")
    public void addView(@PathVariable("userId") long userId) {
        profileServiceImpl.addView(userId);
    }
}