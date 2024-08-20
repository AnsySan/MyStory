package com.clone.twitter.notificationservice.listener;


import com.clone.twitter.notificationservice.client.UserServiceClient;
import com.clone.twitter.notificationservice.event.LikeEvent;
import com.clone.twitter.notificationservice.message.MessageBuilder;
import com.clone.twitter.notificationservice.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class LikeEventListener extends AbstractEventListener<LikeEvent> {

    protected LikeEventListener(ObjectMapper objectMapper,
                                UserServiceClient userServiceClient,
                                MessageBuilder<LikeEvent> messageBuilder,
                                List<NotificationService> notificationServicesList) {
        super(objectMapper, userServiceClient, messageBuilder, notificationServicesList);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, LikeEvent.class, likeEvent -> {
            String textMessage = getMessage(likeEvent, Locale.getDefault());
            sendNotification(likeEvent.getAuthorLikeId(), textMessage);
        });
    }
}