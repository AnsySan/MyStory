package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.PostLikeDto;
import com.clone.twitter.postservice.service.like.PostLikeServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes/post")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeServiceImpl likeService;
    private final UserContext userContext;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Parameter(in = ParameterIn.HEADER, name = "twitter-user-id", required = true)
    public PostLikeDto likePost(@Positive @PathVariable long postId) {
        long userId = userContext.getUserId();
        return likeService.addLike(userId, postId);
    }

    @DeleteMapping("/{postId}")
    @Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)
    public void deleteLikeFromPost(@Positive @PathVariable long postId) {
        long userId = userContext.getUserId();
        likeService.removeLike(userId, postId);
    }
}