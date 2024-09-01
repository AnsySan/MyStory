package com.clone.twitter.userservice.filter;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.stream.Stream;

public class PageFilter implements UserFilter {
    @Override
    public boolean isApplicable(SubscriptionUserFilterDto filters) {
        return filters.getPage() != 0 && filters.getPageSize() != 0;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionUserFilterDto subscriptionUserFilterDto) {
        return users.skip(subscriptionUserFilterDto.getPage() * subscriptionUserFilterDto.getPageSize());
    }
}