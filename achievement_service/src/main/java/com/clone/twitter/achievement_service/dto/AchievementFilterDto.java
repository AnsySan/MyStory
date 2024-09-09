package com.clone.twitter.achievement_service.dto;

import com.clone.twitter.achievement_service.entity.Rarity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementFilterDto {
    private String titlePrefix;
    private String descriptionPrefix;
    private Rarity rarity;
}