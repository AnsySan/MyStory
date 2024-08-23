package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.service.ProfileService;
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
    private final ProfileService profileService;

    @GetMapping("/view/{userId}")
    public void addView(@PathVariable("userId") long userId) {
        profileService.addView(userId);
    }
}