package com.clone.twitter.notificationservice.listener;

import com.clone.twitter.notificationservice.client.UserServiceClient;
import com.clone.twitter.notificationservice.event.ProfileViewEvent;
import com.clone.twitter.notificationservice.exception.ListenerException;
import com.clone.twitter.notificationservice.message.MessageBuilder;
import com.clone.twitter.notificationservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class ProfileViewListener extends AbstractEventListener<ProfileViewEvent> {
    @Autowired
    public ProfileViewListener(ObjectMapper objectMapper, UserServiceClient userServiceClient, MessageBuilder<ProfileViewEvent> messageBuilder, List<NotificationService> notificationServicesList) {
        super(objectMapper, userServiceClient, messageBuilder, notificationServicesList);
    }

    @KafkaListener(topics = "${spring.data.channel.profile-view.name}", groupId = "${spring.data.kafka.group-id}")
    public void listen(String event) {
        if (event == null || event.trim().isEmpty()) {
            log.error("Received empty or null event");
            return;
        }

        try {
            ProfileViewEvent profileViewEvent = objectMapper.readValue(event, ProfileViewEvent.class);
            log.info("Received new profileViewEvent {}", event);
            sendNotification(profileViewEvent.getUserId(), getMessage(profileViewEvent, Locale.ENGLISH));
        } catch (JsonProcessingException e) {
            log.error("Error processing event JSON: {}", event, e);
            throw new SerializationException(e);
        } catch (Exception e) {
            throw new ListenerException(e.getMessage());
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}