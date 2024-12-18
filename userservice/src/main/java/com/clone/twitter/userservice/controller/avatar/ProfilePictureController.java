package com.clone.twitter.userservice.controller.avatar;

import com.clone.twitter.userservice.dto.avatar.UserProfilePictureDto;
import com.clone.twitter.userservice.model.user.UserProfilePicture;
import com.clone.twitter.userservice.service.avatar.ProfilePictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pic")
@Tag(name = "Avatar")
public class ProfilePictureController {
    private final ProfilePictureService profilePicService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save profile picture")
    public @Valid UserProfilePictureDto saveProfilePic(@Positive @Parameter @PathVariable long userId,
                                                       @NotEmpty @RequestParam("file") MultipartFile file) {
        return profilePicService.saveProfilePic(userId, file);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile picture")
    public InputStreamResource getProfilePic(@Positive @Parameter @PathVariable long userId) {
        return profilePicService.getProfilePic(userId);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete profile picture")
    public UserProfilePictureDto deleteProfilePic(@Positive @Parameter @PathVariable long userId) {
        return profilePicService.deleteProfilePic(userId);
    }
}