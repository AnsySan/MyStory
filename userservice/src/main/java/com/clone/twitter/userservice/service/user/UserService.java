package com.clone.twitter.userservice.service.user;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.List;

public interface UserService {

    public UserDto getUser(Long userId);

    public User getUserEntityById(long userId);

    public List<User> getUsersEntityByIds(List<Long> userIds);

    public UserDto create(UserDto userDto);

    public List<UserDto> getUsersByIds(List<Long> ids);

    public void deactivate(long userId);
}
