package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.SubscriptionUserDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.dto.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public void followUser(@RequestParam("followerId") long followerId,
                           @RequestParam("followeeId") long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException( "You can not follow yourself!" );
        }
        subscriptionService.followUser( followerId, followeeId );
    }

    public void unfollowUser(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException( "You can not unfollow yourself!" );
        }
        subscriptionService.unfollowUser( followerId, followeeId );
    }

    public List<SubscriptionUserDto> getFollowers(long followeeId, SubscriptionUserFilterDto filter) {
        return subscriptionService.getFollowers( followeeId, filter );
    }

    public int getFollowersCount(long followeeId) {
        return subscriptionService.getFollowersCount( followeeId );
    }

    public List<SubscriptionUserDto> getFollowing(long followeeId, SubscriptionUserFilterDto filter) {
        return subscriptionService.getFollowing( followeeId, filter );
    }

    public int getFollowingCount(long followerId) {
        return subscriptionService.getFollowingCount( followerId );
    }
}