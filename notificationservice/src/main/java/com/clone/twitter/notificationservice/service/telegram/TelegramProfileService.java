package com.clone.twitter.notificationservice.service.telegram;

import com.clone.twitter.notificationservice.entity.TelegramProfile;
import com.clone.twitter.notificationservice.exception.NotFoundException;
import com.clone.twitter.notificationservice.repository.TelegramProfilesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TelegramProfileService {

    private final TelegramProfilesRepository telegramProfilesRepository;

    @Transactional
    public void save(TelegramProfile telegramProfile) {
        telegramProfilesRepository.save(telegramProfile);
        log.info("Saved new TelegramProfile with chatId={} for user with userId={}", telegramProfile.getChatId(),  telegramProfile.getUserId());
    }

    @Transactional(readOnly = true)
    public TelegramProfile findByUserId(Long userId) {
        return telegramProfilesRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Telegram profile with userId=" + userId + " not found"));
    }

    @Transactional(readOnly = true)
    public Optional<TelegramProfile> findByChatId(long chatId) {
        return telegramProfilesRepository.findByChatId(chatId);
    }

    @Transactional(readOnly = true)
    public boolean existsByChatId(long chatId) {
        return telegramProfilesRepository.existsByChatId(chatId);
    }
}