package com.clone.twitter.notificationservice.service.telegram;

import com.clone.twitter.notificationservice.entity.TelegramProfile;
import com.clone.twitter.notificationservice.repository.TelegramProfilesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TelegramProfileService {

    private final TelegramProfilesRepository telegramProfilesRepository;

    @Transactional
    public void save(TelegramProfile telegramProfile) {
        telegramProfilesRepository.save(telegramProfile);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserName(String userName) {
        return telegramProfilesRepository.existsByUserName(userName);
    }

    public TelegramProfile findByUserId(Long userId) {
        return telegramProfilesRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Telegram profile not found"));
    }
}