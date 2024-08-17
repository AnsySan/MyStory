package com.clone.twitter.postservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDto {
    private Integer  id;
    @NotNull
    @Positive
    private Integer userId;
    @Positive
    private Integer postId;
    @Positive
    private Integer commentId;
}