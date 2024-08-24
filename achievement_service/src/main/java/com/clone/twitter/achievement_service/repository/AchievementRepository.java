package com.clone.twitter.achievement_service.repository;

import com.clone.twitter.achievement_service.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    }