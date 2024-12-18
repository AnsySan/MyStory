package com.clone.twitter.userservice.controller.subscription;

import com.clone.twitter.userservice.dto.subscription.SubscriptionRequestDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.service.subscription.SubscriptonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("subscriptions")
@Tag(name = "Subscriptions")
@Validated
public class SubscriptionController {

    private final SubscriptonService subscriptionService;

    @Operation(summary = "Follow user")
    @PostMapping("following")
    public void followUser(@Valid @ParameterObject @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        subscriptionService.followUser(subscriptionRequestDto);
    }

    @Operation(summary = "Unfollow user")
    @PostMapping("unfollowing")
    public void unfollowUser(@Valid @ParameterObject @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        subscriptionService.unfollowUser(subscriptionRequestDto);
    }

    @Operation(summary = "Get all followers by followeeId with filters")
    @GetMapping("followers/{followeeId}/filter")
    public List<UserDto> getFollowersWithFilter(
            @Positive @Parameter @PathVariable long followeeId,
            @Valid @ParameterObject @RequestBody(required = false) UserFilterDto filter
    ) {
        return subscriptionService.getFollowers(followeeId, filter);
    }

    @Operation(summary = "Get all followers by followeeId")
    @GetMapping("followers/{followeeId}")
    public List<UserDto> getFollowers(@Positive @Parameter @PathVariable long followeeId) {
        return subscriptionService.getFollowers(followeeId, new UserFilterDto());
    }

    @Operation(summary = "Get all followings by followerId with filter")
    @GetMapping("followings/{followerId}/filter")
    public List<UserDto> getFollowingsWithFilter(
            @Positive @Parameter @PathVariable long followerId,
            @Valid @ParameterObject @RequestBody(required = false) UserFilterDto filter
    ) {
        return subscriptionService.getFollowings(followerId, filter);
    }

    @Operation(summary = "Get all followings by followerId")
    @GetMapping("followings/{followerId}")
    public List<UserDto> getFollowings(@Positive @Parameter @PathVariable long followerId) {
        return subscriptionService.getFollowings(followerId, new UserFilterDto());
    }

    @Operation(summary = "Get followers count by foloweeId")
    @GetMapping("followers/count/{followeeId}")
    public int getFollowersCount(@Positive @Parameter @PathVariable long followeeId) {
        return subscriptionService.getFollowersCount(followeeId);
    }

    @Operation(summary = "Get followings count by folowerId")
    @GetMapping("followings/count/{followerId}")
    public int getFollowingsCount(@Positive @Parameter @PathVariable long followerId) {
        return subscriptionService.getFollowingsCount(followerId);
    }
}