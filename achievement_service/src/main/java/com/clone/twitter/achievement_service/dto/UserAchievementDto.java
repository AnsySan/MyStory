package com.clone.twitter.achievement_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAchievementDto {
    private Long id;
    private AchievementDto achievement;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}