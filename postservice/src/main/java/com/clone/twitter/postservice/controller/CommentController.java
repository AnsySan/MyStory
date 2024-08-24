package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserContext userContext;

    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @PostMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@Valid @RequestBody CommentDto comment) {
        return commentService.create(comment, userContext.getUserId());
    }

    @Operation(summary = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto update(@Valid @RequestBody CommentDto comment) throws AuthenticationException {
        Integer userId = userContext.getUserId();
        if (userId != comment.getAuthorId()) {
            throw new AuthenticationException("Only authors of the comment can update it");
        }
        return commentService.update(comment, userId);
    }

    @Operation(summary = "Get all comments for a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of post comments",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getPostComments(@PathVariable @NotNull int postId) {
        return commentService.getPostComments(postId);
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @DeleteMapping("/{commentId}/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Valid @RequestBody CommentDto comment) {
        commentService.delete(comment, userContext.getUserId());
    }

}