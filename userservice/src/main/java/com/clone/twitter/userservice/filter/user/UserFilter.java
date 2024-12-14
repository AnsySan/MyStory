package com.clone.twitter.userservice.filter.user;


import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.stream.Stream;

public interface UserFilter {

    boolean isAcceptable(UserFilterDto userFilterDto);

    Stream<User> applyFilter(Stream<User> users, UserFilterDto userFilterDto);
}