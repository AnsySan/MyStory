package com.clone.twitter.userservice.validator;

import com.clone.twitter.userservice.model.user.User;

import java.util.List;

public interface UserValidator {

    public User validateUserExistence(Long userId);

    public List<User> validateUsersExistence(List<Long> userIds);
}
