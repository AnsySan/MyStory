package com.clone.twitter.achievement_service.service;

import com.clone.twitter.achievement_service.entity.Achievement;
import com.clone.twitter.achievement_service.entity.AchievementProgress;
import com.clone.twitter.achievement_service.entity.UserAchievement;

public interface AchievementService {

    public boolean hasAchievement(long userId, long achievementId);

    public AchievementProgress getProgress(long userId, Achievement achievement);

    public void giveAchievement(UserAchievement userAchievement);

    public Achievement getAchievementByName(String name);

    public void incrementAchievementProgress(AchievementProgress achievementProgress);

    public void updateAchievementProgress(AchievementProgress achievementProgress);
}
