package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.ProfileViewEventDto;
import com.clone.twitter.userservice.entity.User;
import com.clone.twitter.userservice.mapper.UserMapper;
import com.clone.twitter.userservice.publisher.ProfileViewEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService  {
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final ProfileViewEventPublisher profileViewEventPublisher;
    private final UserService userService;

    @Transactional
    public void addView(long userId) {
        User user = userService.getUserEntityById(userId);
        long viewerId = userContext.getUserId();
        ProfileViewEventDto event = new ProfileViewEventDto(userId, viewerId, LocalDateTime.now());
        if(event.getObserverId() != event.getObserverId()){
            profileViewEventPublisher.publish(event);
        }
        userMapper.toDto(user);
    }
}