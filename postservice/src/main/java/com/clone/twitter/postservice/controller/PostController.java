package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.dto.PostDto;
import com.clone.twitter.postservice.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class PostController {

    private final PostService postService;


    @Operation(
            summary = "Creating a draft post",
            description = "Exactly one author user or project creates a draft of the post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created"),
            @ApiResponse(responseCode = "500", description = "There is no access to the database of users or projects")
    })
    @PostMapping("/create")
    public PostDto createPost(@RequestBody @Valid PostDto postDto) {
        return postService.createPost(postDto);
    }

    @Operation(summary = "Publishing a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post published"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when publishing a post")
    })
    @PutMapping("/publish/{postId}")
    public PostDto publishPost(@PathVariable @Positive(message = "Id must be greater than zero") long postId) {
        return postService.publishPost(postId);
    }

    @Operation(
            summary = "Update existing post",
            description = "Gets PostDto and updates existing post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the post")
    })
    @PutMapping("/{postId}")
    public PostDto updatePost(@PathVariable long postId, @RequestBody @Valid PostDto postDto) {
        return postService.updatePost(postId, postDto);
    }

    @Operation(summary = "Deleting a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when deleting a post")
    })
    @DeleteMapping("/{postId}")
    public PostDto deletePost(@PathVariable @Positive(message = "Id must be greater than zero") long postId) {
        return postService.deletePost(postId);
    }

    @Operation(summary = "Getting a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post received"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when receiving a post")
    })
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable @Positive(message = "Id must be greater than zero") long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("drafts-by-user/{userId}")
    public List<PostDto> findAllPostDraftsByAuthorId(@PathVariable Long userId) {
        return postService.findPostDraftsByUserAuthorId(userId);
    }

    @GetMapping("publication-by-user/{userId}")
    public List<PostDto> findAllPostPublicationByAuthorId(@PathVariable Long userId) {
        return postService.findPostPublicationsByUserAuthorId(userId);
    }
}