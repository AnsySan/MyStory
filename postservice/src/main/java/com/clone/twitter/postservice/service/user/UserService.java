package com.clone.twitter.postservice.service.user;

import com.clone.twitter.postservice.dto.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(long userId);

    List<UserDto> getAllUsers();
}