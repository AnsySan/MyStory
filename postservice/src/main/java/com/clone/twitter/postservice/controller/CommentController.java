package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CommentController {
    private final CommentService commentService;
    private final UserContext userContext;

    @PostMapping
    public CommentDto create(@Valid @RequestBody CommentDto comment) {
        return commentService.create(comment, userContext.getUserId());
    }

    @PutMapping
    public CommentDto update(@Valid @RequestBody CommentDto comment) throws AuthenticationException {
        Integer userId = userContext.getUserId();
        if (userId != comment.getAuthorId()) {
            throw new AuthenticationException("Only authors of the comment can update it");
        }
        return commentService.update(comment, userId);
    }

    @GetMapping("/{postId}/comments")
    public List<CommentDto> getPostComments(@PathVariable @NotNull int postId) {
        return commentService.getPostComments(postId);
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody CommentDto comment) {
        commentService.delete(comment, userContext.getUserId());
    }

}