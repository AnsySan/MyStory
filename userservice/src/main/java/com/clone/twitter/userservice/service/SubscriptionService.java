package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.SearchAppearanceEvent;
import com.clone.twitter.userservice.dto.SubscriptionUserDto;
import com.clone.twitter.userservice.entity.User;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.dto.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.filter.UserFilter;
import com.clone.twitter.userservice.mapper.SubscriptionUserMapper;
import com.clone.twitter.userservice.publisher.SearchAppearanceEventPublisher;
import com.clone.twitter.userservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionUserMapper userMapper;
    private final List<UserFilter> userFilters;
    private final SearchAppearanceEventPublisher searchAppearanceEventPublisher;
    private final UserContext userContext;

    @Transactional
    public void followUser(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException("You can not follow yourself!");
        }
        if (subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new DataValidationException("This subscription already exists!");
        }

        subscriptionRepository.followUser(followerId, followeeId);

    }

    public void unfollowUser(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException("You can not unfollow yourself!");
        }
        subscriptionRepository.unfollowUser(followerId, followeeId);
    }

    public List<SubscriptionUserDto> getFollowers(long followeeId, SubscriptionUserFilterDto filter) {
        Stream<User> users = subscriptionRepository.findByFolloweeId(followeeId);
        return filterUsers(users, filter);
    }

    public int getFollowersCount(long followeeId) {
        return subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
    }

    public List<SubscriptionUserDto> getFollowing(long followeeId, SubscriptionUserFilterDto filter) {
        Stream<User> users = subscriptionRepository.findByFolloweeId(followeeId);
        return filterUsers(users, filter);
    }

    public int getFollowingCount(long followerId) {
        return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
    }

    private List<SubscriptionUserDto> filterUsers(Stream<User> users, SubscriptionUserFilterDto filters) {
        Stream<User> filteredUsers = users;
        for (UserFilter userFilter : userFilters) {
            if (userFilter.isApplicable(filters)) {
                filteredUsers = userFilter.apply(filteredUsers, filters);
            }
        }

        List<SubscriptionUserDto> filteredUsersList = userMapper.toDto(users.toList());
        List<Long> userIds = filteredUsersList.stream().map(SubscriptionUserDto::getId).toList();

        userIds.forEach(userId -> {
            SearchAppearanceEvent event = new SearchAppearanceEvent();
            event.setViewedUserId(userId);
            event.setViewerUserId(userContext.getUserId());
            event.setViewingTime(LocalDateTime.now());
            searchAppearanceEventPublisher.publish(event);
        });

        return filteredUsersList;
    }
}