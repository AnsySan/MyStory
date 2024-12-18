package com.clone.twitter.userservice.service.subscription;

import com.clone.twitter.userservice.dto.subscription.SubscriptionRequestDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.mapper.user.UserMapper;
import com.clone.twitter.userservice.repository.subscription.SubscriptionRepository;
import com.clone.twitter.userservice.service.user.filter.UserFilterService;
import com.clone.twitter.userservice.validator.subscription.SubscriptionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptonService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserMapper userMapper;
    private final UserFilterService userFilterService;
    private final SubscriptionValidator subscriptionValidator;

    @Override
    @Transactional
    public void followUser(SubscriptionRequestDto subscriptionRequestDto) {
        subscriptionValidator.validateSubscriptionExistence(subscriptionRequestDto);
        subscriptionValidator.validateFollowerAndFolloweeIds(subscriptionRequestDto);
        subscriptionRepository.followUser(subscriptionRequestDto.getFollowerId(), subscriptionRequestDto.getFolloweeId());
    }

    @Override
    @Transactional
    public void unfollowUser(SubscriptionRequestDto subscriptionRequestDto) {
        subscriptionValidator.validateFollowerAndFolloweeIds(subscriptionRequestDto);
        subscriptionRepository.unfollowUser(subscriptionRequestDto.getFollowerId(), subscriptionRequestDto.getFolloweeId());
    }

    @Override
    @Transactional
    public List<UserDto> getFollowers(long followeeId, UserFilterDto filter) {
        return userFilterService.applyFilters(subscriptionRepository.findByFolloweeId(followeeId), filter)
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<UserDto> getFollowings(long followerId, UserFilterDto filter) {
        return userFilterService.applyFilters(subscriptionRepository.findByFollowerId(followerId), filter)
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public int getFollowersCount(long followeeId) {
        return subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
    }

    @Override
    @Transactional
    public int getFollowingsCount(long followerId) {
        return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
    }
}