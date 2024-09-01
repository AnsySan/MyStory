package com.clone.twitter.userservice.filter;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.stream.Stream;

public class PhonePatternFilter implements UserFilter {
    @Override
    public boolean isApplicable(SubscriptionUserFilterDto subscriptionUserFilterDto) {
        return subscriptionUserFilterDto.getPhonePattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionUserFilterDto subscriptionUserFilterDto) {
        return users.filter(user -> user.getPhone().matches(subscriptionUserFilterDto.getPhonePattern()));
    }
}