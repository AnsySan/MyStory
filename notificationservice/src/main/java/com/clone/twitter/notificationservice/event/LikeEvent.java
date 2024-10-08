package com.clone.twitter.notificationservice.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeEvent {
    @NotNull
    private Long authorLikeId;
    @Positive
    private Long authorPostId;
    @Positive
    private Long postId;
    @Positive
    private Long authorCommentId;
    @Positive
    private Long commentId;
    private LocalDateTime createdAt;
}