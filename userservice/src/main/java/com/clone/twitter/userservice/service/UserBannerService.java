package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBannerService {
    private final UserService userService;

    @Transactional
    public void banUserById(Long userId){
        User user = userService.getUserEntityById(userId);
        user.setBanned(true);
    }

    @Transactional
    public void banUsersByIds(List<Long> userIds){
        List<User> users = userService.getUsersEntityByIds(userIds);
        users.forEach(user -> user.setBanned(true));
    }
}