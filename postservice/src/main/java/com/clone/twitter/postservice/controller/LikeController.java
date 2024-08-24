package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.LikeDto;
import com.clone.twitter.postservice.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
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

    @PostMapping("/comment/{commentId}")
    @Operation(summary = "Like comment")
    public LikeDto likeComment(@RequestBody @Validated LikeDto likeDto) {
        return likeService.likeComment(likeDto);
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "Remove like from comment")
    public void deleteLikeFromComment(@Valid @PathVariable int commentId) {
        likeService.deleteLikeFromComment(commentId);
    }
}