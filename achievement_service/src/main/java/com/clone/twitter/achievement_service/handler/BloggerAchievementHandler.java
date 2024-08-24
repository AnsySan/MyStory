package com.clone.twitter.achievement_service.handler;

import com.clone.twitter.achievement_service.dto.FollowerEvent;
import com.clone.twitter.achievement_service.repository.AchievementProgressRepository;
import com.clone.twitter.achievement_service.repository.UserAchievementRepository;
import com.clone.twitter.achievement_service.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class BloggerAchievementHandler extends AbstractEventHandler<FollowerEvent> {

    @Value("${achievements.title.blogger}")
    private String blogger;

    public BloggerAchievementHandler(AchievementService achievementService,
                                     UserAchievementRepository userAchievementRepository,
                                     AchievementProgressRepository achievementProgressRepository) {
        super(achievementService, userAchievementRepository, achievementProgressRepository);
    }

    @Override
    protected String getAchievementName() {
        return blogger;
    }

}