package com.clone.twitter.achievement_service.repository;

import com.clone.twitter.achievement_service.entity.AchievementProgress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementProgressRepository extends CrudRepository<AchievementProgress, Long> {

    @Query(value = """
            SELECT ap
            FROM AchievementProgress ap
            WHERE ap.userId = :userId AND ap.achievement.id = :achievementId
    """)
    Optional<AchievementProgress> findByUserIdAndAchievementId(long userId, long achievementId);

    @Query(nativeQuery = true, value = """
            INSERT INTO user_achievement_progress (user_id, achievement_id, current_points)
            VALUES (:userId, :achievementId, 0)
            ON CONFLICT DO NOTHING
    """)
    @Modifying
    void createProgressIfNecessary(long userId, long achievementId);

    List<AchievementProgress> findByUserId(long userId);
}