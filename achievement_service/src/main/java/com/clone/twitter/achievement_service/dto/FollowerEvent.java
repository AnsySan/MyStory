package com.clone.twitter.achievement_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowerEvent implements Event {

    @NotNull
    private long followerId;
    @NotNull
    private long followeeId;
    @NotNull
    private LocalDateTime subscriptionDateTime;

    @Override
    public long getAchievementHolderId() {
        return followeeId;
    }
}