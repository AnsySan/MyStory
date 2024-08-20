package com.clone.twitter.notificationservice.listener;

import com.clone.twitter.notificationservice.client.UserServiceClient;
import com.clone.twitter.notificationservice.event.CommentEvent;
import com.clone.twitter.notificationservice.message.MessageBuilder;
import com.clone.twitter.notificationservice.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> {
    protected CommentEventListener(ObjectMapper objectMapper,
                                   UserServiceClient userServiceClient,
                                   MessageBuilder<CommentEvent> messageBuilder,
                                   List<NotificationService> notificationServicesList) {
        super(objectMapper, userServiceClient, messageBuilder, notificationServicesList);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, CommentEvent.class, commentEvent -> {
            String textMessage = getMessage(commentEvent, Locale.getDefault());
            sendNotification(commentEvent.getAuthorOfPostId(), textMessage);
        });
    }
}