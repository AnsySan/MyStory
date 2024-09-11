package com.clone.twitter.userservice.service.subscription;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserDto;
import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;

import java.util.List;

public interface SubscriptonService {

    public void followUser(long followerId, long followeeId);

    public void unfollowUser(long followerId, long followeeId);

    public List<SubscriptionUserDto> getFollowers(long followeeId, SubscriptionUserFilterDto filter);

    public int getFollowersCount(long followeeId);

    public List<SubscriptionUserDto> getFollowing(long followeeId, SubscriptionUserFilterDto filter);

    public int getFollowingCount(long followerId);
}