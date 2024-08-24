package com.clone.twitter.achievement_service.cache;

import com.clone.twitter.achievement_service.entity.Achievement;
import com.clone.twitter.achievement_service.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private final Map<String, Achievement> achievementsByName = new HashMap<>();

    @PostConstruct
    public void init() {
        List<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(achievement ->
                achievementsByName.put(achievement.getTitle(), achievement)
        );
    }

    public Optional<Achievement> getAchievement(String title) {
        return Optional.ofNullable(achievementsByName.get(title));
    }

    public HashMap<String, Achievement> getAchievementsByName() {
        return (HashMap<String, Achievement>) achievementsByName;
    }
}