package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @Parameter(in = ParameterIn.HEADER, name = "twitter-user-id", required = true)
    public CommentDto createComment(@PathVariable("postId") long postId,
                                    @RequestBody @Valid CommentDto commentDto) {
        long userId = userContext.getUserId();
        return commentService.createComment(commentDto, userId, postId);
    }

    @Operation(summary = "Get all comments for a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of post comments",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getPostComments(@PathVariable("postId") long postId) {
        return commentService.getPostComments(postId);
    }

    @Operation(summary = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@PathVariable("commentId") long commentId,
                                    @RequestBody @Valid CommentDto commentDto) {
        long userId = userContext.getUserId();
        return commentService.updateComment(commentDto, userId, commentId);
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @DeleteMapping("/{commentId}/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto deleteComment(@PathVariable("commentId") long commentId,
                                    @PathVariable("postId") long postId) {
        long userId = userContext.getUserId();
        return commentService.deleteComment(commentId, postId, userId);
    }
}