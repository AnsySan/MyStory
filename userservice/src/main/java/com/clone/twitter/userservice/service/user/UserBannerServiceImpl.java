package com.clone.twitter.userservice.service.user;

import com.clone.twitter.userservice.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBannerServiceImpl implements UserBannerService {
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public void banUserById(Long userId){
        User user = userServiceImpl.getUserEntityById(userId);
        user.setBanned(true);
    }

    @Override
    @Transactional
    public void banUsersByIds(List<Long> userIds){
        List<User> users = userServiceImpl.getUsersEntityByIds(userIds);
        users.forEach(user -> user.setBanned(true));
    }
}