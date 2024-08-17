package com.clone.twitter.userservice.filter;

import com.clone.twitter.userservice.dto.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.entity.User;

import java.util.stream.Stream;

public class ContactPatternFilter implements UserFilter {
    @Override
    public boolean isApplicable(SubscriptionUserFilterDto filters) {
        return filters.getContactPattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionUserFilterDto subscriptionUserFilterDto) {
        return users.filter(user -> user.getContacts().contains(subscriptionUserFilterDto.getContactPattern()));
    }
}