package com.clone.twitter.userservice.service.user.filter;

import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.filter.user.UserFilter;
import com.clone.twitter.userservice.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserFilterServiceImpl implements UserFilterService {

    private final List<UserFilter> userFilters;

    @Override
    public Stream<User> applyFilters(Stream<User> users, UserFilterDto userFilterDto) {
        if (userFilterDto != null) {
            for (UserFilter userFilter : userFilters) {
                if (userFilter.isAcceptable(userFilterDto)) {
                    users = userFilter.applyFilter(users, userFilterDto);
                }
            }
        }

        return users;
    }
}