package com.clone.twitter.userservice.service.subscription;

import com.clone.twitter.userservice.dto.subscription.SubscriptionRequestDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.dto.user.UserFilterDto;

import java.util.List;

public interface SubscriptonService {

    void followUser(SubscriptionRequestDto subscriptionRequestDto);

    void unfollowUser(SubscriptionRequestDto subscriptionRequestDto);

    List<UserDto> getFollowers(long followeeId, UserFilterDto filter);

    List<UserDto> getFollowings(long followerId, UserFilterDto filter);

    int getFollowersCount(long followeeId);

    int getFollowingsCount(long followerId);
}