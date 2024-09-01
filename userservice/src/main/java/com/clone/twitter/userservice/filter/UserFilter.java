package com.clone.twitter.userservice.filter;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.model.user.User;

import java.util.stream.Stream;

public interface UserFilter {
    boolean isApplicable(SubscriptionUserFilterDto filters);

    Stream<User> apply(Stream<User> users, SubscriptionUserFilterDto subscriptionUserFilterDto);
}