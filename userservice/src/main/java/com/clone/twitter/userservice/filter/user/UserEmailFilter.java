package com.clone.twitter.userservice.filter.user;

import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.model.user.User;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class UserEmailFilter implements UserFilter {

    @Override
    public boolean isAcceptable(UserFilterDto userFilterDto) {
        return userFilterDto.getCountryPattern() != null;
    }

    @Override
    public Stream<User> applyFilter(Stream<User> users, UserFilterDto userFilterDto) {
        return users.filter(user -> user.getEmail().startsWith(userFilterDto.getEmailPattern()));
    }
}