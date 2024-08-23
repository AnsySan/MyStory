package com.clone.twitter.notificationservice.message;

import com.clone.twitter.notificationservice.client.UserServiceClient;
import com.clone.twitter.notificationservice.dto.UserDto;
import com.clone.twitter.notificationservice.event.ProfileViewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ProfileViewEventMessageBuilder implements MessageBuilder<ProfileViewEvent> {

    private final UserServiceClient userServiceClient;
    private final MessageSource messageSource;

    @Override
    public Class<ProfileViewEvent> getEventType() {
        return ProfileViewEvent.class;
    }

    @Override
    public String buildMessage(ProfileViewEvent event, Locale locale) {
        UserDto viewer = userServiceClient.getUser(event.getViewerId());
        String defaultMessage = messageSource.getMessage("profile.view", new Object[]{viewer.getUsername()}, Locale.ENGLISH);
        return messageSource.getMessage("profile.view", new Object[]{viewer.getUsername()}, defaultMessage, locale);
    }
}