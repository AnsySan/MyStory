package com.clone.twitter.postservice.event;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentEvent {
    @NotNull
    private Integer postId;
    @NotNull
    private Integer authorId;
    @NotNull
    private Integer commentId;
    @NotNull
    private LocalDateTime commentedAt;
}