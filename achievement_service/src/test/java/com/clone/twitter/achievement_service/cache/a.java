package com.clone.twitter.achievement_service.cache;

import com.clone.twitter.achievement_service.entity.Achievement;
import com.clone.twitter.achievement_service.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AchievementCacheTest {
    @Mock
    AchievementRepository achievementRepository;
    @InjectMocks
    AchievementCache achievementCache;
    HashMap<String, Achievement> map = new HashMap<>();
    Achievement firstAchievement;

    @BeforeEach
    void setUp() {
        firstAchievement = Achievement.builder()
                .title("First Achievement")
                .build();
        map.put(firstAchievement.getTitle(), firstAchievement);
    }

    @Test
    public void testInit() {
        Mockito.when(achievementRepository.findAll()).thenReturn(List.of(firstAchievement));
        achievementCache.init();
        HashMap<String, Achievement> cachedMap = achievementCache.getAchievementsByName();
        assertEquals(map, cachedMap);
    }

}