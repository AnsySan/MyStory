package com.clone.twitter.userservice.filter;

import com.clone.twitter.userservice.dto.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.entity.User;

import java.util.stream.Stream;

public class AboutPatternFilter implements UserFilter {
    @Override
    public boolean isApplicable(SubscriptionUserFilterDto filters) {
        return filters.getAboutPattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionUserFilterDto subscriptionUserFilterDto) {
        return users.filter(user -> user.getAboutMe().contains(subscriptionUserFilterDto.getAboutPattern()));
    }
}