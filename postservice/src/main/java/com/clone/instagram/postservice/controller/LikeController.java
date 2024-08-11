package com.clone.instagram.postservice.controller;

import com.clone.instagram.postservice.dto.LikeDto;
import com.clone.instagram.postservice.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    @Operation(summary = "Like post")
    public LikeDto likePost(@RequestBody @Validated LikeDto likeDto) {
        return likeService.likePost(likeDto);
    }

    @DeleteMapping("/post/{postId}")
    @Operation(summary = "Remove like from post")
    public void deleteLikeFromPost(@Valid @PathVariable int postId) {
        likeService.deleteLikeFromPost(postId);
    }
}