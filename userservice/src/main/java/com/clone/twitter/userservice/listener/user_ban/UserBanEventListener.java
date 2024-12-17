package com.clone.twitter.userservice.listener.user_ban;

import com.clone.twitter.userservice.event.user_ban.UserBanEvent;
import com.clone.twitter.userservice.listener.AbstractEventListener;
import com.clone.twitter.userservice.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class UserBanEventListener extends AbstractEventListener<UserBanEvent> {

    private final UserService userService;

    public UserBanEventListener(ObjectMapper objectMapper, UserService userService) {
        super(objectMapper);
        this.userService = userService;
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        handleEvent(message, UserBanEvent.class, event -> {
            userService.banUserByIds(event.getUserIds());
        });
    }
}