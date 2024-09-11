package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.avatar.UserProfilePictureDto;
import com.clone.twitter.userservice.service.avatar.ProfilePictureServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pic")
public class ProfilePictureController {
    private final ProfilePictureServiceImpl profilePictureServiceImpl;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save profile picture")
    public @Valid UserProfilePictureDto saveProfilePic(@Positive @Parameter @PathVariable long userId,
                                                           @NotEmpty @RequestParam("file") MultipartFile file) {
        return profilePictureServiceImpl.saveProfilePic(userId, file);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile picture")
    public InputStreamResource getProfilePic(@Positive @Parameter @PathVariable long userId) {
        return profilePictureServiceImpl.getProfilePic(userId);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete profile picture")
    public UserProfilePictureDto deleteProfilePic(@Positive @Parameter @PathVariable long userId) {
        return profilePictureServiceImpl.deleteProfilePic(userId);
    }
}