package com.clone.twitter.userservice.service.user.filter;


import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.stream.Stream;

public interface UserFilterService {

    Stream<User> applyFilters(Stream<User> users, UserFilterDto userFilterDto);
}
