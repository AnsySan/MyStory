package com.clone.twitter.userservice.validator.subscription;

import com.clone.twitter.userservice.dto.subscription.SubscriptionRequestDto;

public interface SubscriptionValidator {

    void validateSubscriptionExistence(SubscriptionRequestDto subscriptionRequestDto);

    void validateFollowerAndFolloweeIds(SubscriptionRequestDto subscriptionRequestDto);
}