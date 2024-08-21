package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.SubscriptionUserDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.dto.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subscriptions")
public class FollowingController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/following")
    public void followUser(@RequestParam("followerId") long followerId,
                           @RequestParam("followeeId") long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException( "You can not follow yourself!" );
        }
        subscriptionService.followUser( followerId, followeeId );
    }
    @PostMapping("/unfollowing")
    public void unfollowUser(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException( "You can not unfollow yourself!" );
        }
        subscriptionService.unfollowUser( followerId, followeeId );
    }
    @GetMapping("followers/{followeeId}/filter")
    public List<SubscriptionUserDto> getFollowers(long followeeId, SubscriptionUserFilterDto filter) {
        return subscriptionService.getFollowers( followeeId, filter );
    }
    @GetMapping("followers/count/{followeeId}")
    public int getFollowersCount(long followeeId) {
        return subscriptionService.getFollowersCount( followeeId );
    }
    @GetMapping("followings/{followerId}")
    public List<SubscriptionUserDto> getFollowing(long followeeId, SubscriptionUserFilterDto filter) {
        return subscriptionService.getFollowing( followeeId, filter );
    }
    @GetMapping("followings/count/{followerId}")
    public int getFollowingCount(long followerId) {
        return subscriptionService.getFollowingCount( followerId );
    }
}