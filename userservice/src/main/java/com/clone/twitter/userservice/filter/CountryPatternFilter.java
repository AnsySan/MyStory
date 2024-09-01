package com.clone.twitter.userservice.filter;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.stream.Stream;

public class CountryPatternFilter implements UserFilter {
    @Override
    public boolean isApplicable(SubscriptionUserFilterDto filters) {
        return filters.getCountryPattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionUserFilterDto subscriptionUserFilterDto) {
        return users.filter(user -> user.getCountry().equals(subscriptionUserFilterDto.getCountryPattern()));
    }
}