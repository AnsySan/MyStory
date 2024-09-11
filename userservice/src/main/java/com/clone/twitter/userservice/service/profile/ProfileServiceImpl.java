package com.clone.twitter.userservice.service.profile;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.profile.ProfileViewEventDto;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.mapper.user.UserMapper;
import com.clone.twitter.userservice.publisher.ProfileViewEventPublisher;
import com.clone.twitter.userservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final ProfileViewEventPublisher profileViewEventPublisher;
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public void addView(long userId) {
        User user = userServiceImpl.getUserEntityById(userId);
        long viewerId = userContext.getUserId();
        ProfileViewEventDto event = new ProfileViewEventDto(userId, viewerId, LocalDateTime.now());
        if(event.getObserverId() != event.getObserverId()){
            profileViewEventPublisher.publish(event);
        }
        userMapper.toDto(user);
    }
}