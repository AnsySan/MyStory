package com.clone.twitter.userservice.service.user;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    User findUserById(long id);

    List<UserDto> findPremiumUsers(UserFilterDto filterDto);

    void deactivateUserById(Long id);

    List<UserDto> getUsersByIds(List<Long> ids);

    UserDto getUserById(long userId);

    void banUserByIds(List<Long> userIds);
}