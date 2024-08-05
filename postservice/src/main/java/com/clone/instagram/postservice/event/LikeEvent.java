package com.clone.instagram.postservice.event;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Builder(toBuilder = true)
public class LikeEvent {
    @Positive
    private Integer id;
    @NotNull
    private Integer authorLikeId;
    @Positive
    private Integer authorPostId;
    @Positive
    private Integer postId;
    @Positive
    private Integer authorCommentId;
    @Positive
    private Integer commentId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
}