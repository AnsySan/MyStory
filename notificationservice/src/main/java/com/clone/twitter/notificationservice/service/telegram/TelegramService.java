package com.clone.twitter.notificationservice.service.telegram;

import com.clone.twitter.notificationservice.dto.UserDto;
import com.clone.twitter.notificationservice.enums.PreferredContact;
import com.clone.twitter.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TelegramService implements NotificationService {

    private final TelegramBot telegramBot;

    @Override
    public void send(UserDto user, String message) {
        telegramBot.toSendMessage(user.getId(), message);
    }

    @Override
    public PreferredContact getPreferredContact() {
        return PreferredContact.TELEGRAM;
    }
}