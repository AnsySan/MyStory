package com.clone.twitter.notificationservice.message;

import com.clone.twitter.notificationservice.client.UserServiceClient;
import com.clone.twitter.notificationservice.event.CommentEvent;
import com.clone.twitter.notificationservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentEventMessageBuilder implements MessageBuilder<CommentEvent> {

    protected final UserServiceClient userServiceClient;
    protected final MessageSource messageSource;

    @Value("${commentEvent.comment}")
    private String commentCode;

    @Override
    public Class<CommentEvent> getEventType() {
        return CommentEvent.class;
    }

    @Override
    public String buildMessage(CommentEvent event, Locale locale) {
        UserDto user = userServiceClient.getUser(event.getAuthorOfPostId());
        Long publication = event.getCommentId();

        if (commentCode != null) {
            return messageSource.getMessage(commentCode, new Object[]{user.getUsername(), publication}, locale);
        } else {
            log.error("The message code for the CommentEvent event could not be found {}", event);
            throw new IllegalArgumentException("The message code for the CommentEvent event could not be found");
        }
    }
}