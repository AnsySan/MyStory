package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.service.subscription.SubscriptionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subscriptions")
public class FollowingController {
    private final SubscriptionServiceImpl subscriptionServiceImpl;

    @PostMapping("/following")
    public void followUser(@RequestParam("followerId") long followerId,
                           @RequestParam("followeeId") long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException( "You can not follow yourself!" );
        }
        subscriptionServiceImpl.followUser( followerId, followeeId );
    }
    @PostMapping("/unfollowing")
    public void unfollowUser(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException( "You can not unfollow yourself!" );
        }
        subscriptionServiceImpl.unfollowUser( followerId, followeeId );
    }
    @GetMapping("followers/{followeeId}/filter")
    public List<SubscriptionUserDto> getFollowers(long followeeId, SubscriptionUserFilterDto filter) {
        return subscriptionServiceImpl.getFollowers( followeeId, filter );
    }
    @GetMapping("followers/count/{followeeId}")
    public int getFollowersCount(long followeeId) {
        return subscriptionServiceImpl.getFollowersCount( followeeId );
    }
    @GetMapping("followings/{followerId}")
    public List<SubscriptionUserDto> getFollowing(long followeeId, SubscriptionUserFilterDto filter) {
        return subscriptionServiceImpl.getFollowing( followeeId, filter );
    }
    @GetMapping("followings/count/{followerId}")
    public int getFollowingCount(long followerId) {
        return subscriptionServiceImpl.getFollowingCount( followerId );
    }
}