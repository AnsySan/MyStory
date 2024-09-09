package com.clone.twitter.postservice.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeDto {
    private Long id;
    @Positive
    private Long userId;
    @Positive
    private Long commentId;
}