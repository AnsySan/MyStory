package com.clone.instagram.postservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDto {

    private Integer id;
    @NotNull(message = "User ID must be specified")
    private Integer userId;
    private Integer postId;
}