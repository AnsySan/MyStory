package com.clone.twitter.postservice.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer id;
    @NotNull
    private Integer authorId;
    @Size(min = 1, max = 4096, message = "Comment should be between 1 and 4096 symbols")
    private String content;
    private long likesCount;
    @NotNull
    private long postId;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;
}