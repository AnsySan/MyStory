package com.clone.twitter.achievement_service.dto;

import com.clone.twitter.achievement_service.entity.Rarity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementDto {
    private Long id;
    private String title;
    private String description;
    private Rarity rarity;
    private long points;
}