package com.clone.twitter.achievement_service.service;

import com.clone.twitter.achievement_service.cache.AchievementCache;
import com.clone.twitter.achievement_service.entity.Achievement;
import com.clone.twitter.achievement_service.entity.AchievementProgress;
import com.clone.twitter.achievement_service.entity.UserAchievement;
import com.clone.twitter.achievement_service.repository.AchievementProgressRepository;
import com.clone.twitter.achievement_service.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService{

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementCache achievementCache;

    @Override
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Override
    @Transactional
    public AchievementProgress getProgress(long userId, Achievement achievement) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId())
                .orElseGet(() -> saveProgressWithUserIdAndAchievement(userId, achievement));
    }

    @Override
    @Transactional
    public void giveAchievement(UserAchievement userAchievement) {
        if (!userAchievementRepository.existsByUserIdAndAchievementId(userAchievement.getUserId(),
                userAchievement.getAchievement().getId())) {
            userAchievementRepository.save(userAchievement);
        }
    }

    @Override
    public Achievement getAchievementByName(String name) {
        Optional<Achievement> achievement = achievementCache.getAchievement(name);
        if (achievement.isPresent()) {
            return achievement.get();
        } else {
            throw new EntityNotFoundException("No achievement with name " + name);
        }
    }

    @Override
    public void incrementAchievementProgress(AchievementProgress achievementProgress) {
        AtomicLong currentPoints = new AtomicLong(achievementProgress.getCurrentPoints());
        currentPoints.incrementAndGet();
        achievementProgress.setCurrentPoints(currentPoints.get());
    }

    @Override
    public void updateAchievementProgress(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
    }

    private AchievementProgress saveProgressWithUserIdAndAchievement(long userId, Achievement achievement) {
        return achievementProgressRepository.save(AchievementProgress.builder()
                .userId(userId)
                .achievement(achievement)
                .build()
        );
    }
}