package com.clone.twitter.achievement_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AchievementEventDto implements Event {
    private Long authorId;
    private Long achievementId;
    private String title;

    @Override
    public long getAchievementHolderId() {
        return achievementId;
    }
}