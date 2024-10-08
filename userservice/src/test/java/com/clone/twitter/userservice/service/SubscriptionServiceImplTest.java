package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.SearchAppearanceEvent;
import com.clone.twitter.userservice.dto.subscription.SubscriptionUserDto;
import com.clone.twitter.userservice.dto.subscription.SubscriptionUserFilterDto;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.filter.*;
import com.clone.twitter.userservice.mapper.subscription.SubscriptionUserMapper;
import com.clone.twitter.userservice.publisher.SearchAppearanceEventPublisher;
import com.clone.twitter.userservice.repository.SubscriptionRepository;
import com.clone.twitter.userservice.service.subscription.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {
    private static Long userId1;
    private static Long userId2;
    private final List<UserFilter> userFilters = new ArrayList<>();

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private SearchAppearanceEventPublisher searchAppearanceEventPublisher;
    @Mock
    private UserContext userContext;

    @Spy
    private SubscriptionUserMapper userMapper = Mappers.getMapper(SubscriptionUserMapper.class);

    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @BeforeEach
    public void initialize() {
        userId1 = 1000L;
        userId2 = 2000L;
        userFilters.add(new CityPatternFilter());
        userFilters.add(new CountryPatternFilter());
        userFilters.add(new NamePatternFilter());
        subscriptionServiceImpl = new SubscriptionServiceImpl(subscriptionRepository,
                userMapper,
                userFilters,
                searchAppearanceEventPublisher,
                userContext);
    }

    @Test
    public void testFollowUserThrowsExceptionWhenFollowsItself() {
        assertThrows(DataValidationException.class, () -> subscriptionServiceImpl.followUser(userId1, userId1));
    }

    @Test
    public void testFollowUserThrowsExceptionWhenSubscriptionExists() {
        when(subscriptionRepository.existsByFollowerIdAndFolloweeId(userId1, userId2)).thenReturn(true);
        assertThrows(DataValidationException.class, () -> subscriptionServiceImpl.followUser(userId1, userId2));
    }

    @Test
    public void testFollowUser() {
        subscriptionServiceImpl.followUser(userId1, userId2);
        verify(subscriptionRepository, times(1)).followUser(userId1, userId2);
    }

    @Test
    public void testUnfollowUserThrowsExceptionWhenUnfollowYourself() {
        assertThrows(DataValidationException.class, () -> subscriptionServiceImpl.unfollowUser(userId2, userId2));
    }

    @Test
    public void testUnfollowUser() {
        subscriptionServiceImpl.unfollowUser(userId1, userId2);
        verify(subscriptionRepository, times(1)).unfollowUser(userId1, userId2);
    }

    @Test
    public void testGetFollowers() {
        User user1 = new User();
        user1.setId(userId1);
        user1.setFollowers(List.of(User.builder().id(userId2).build()));
        when(subscriptionRepository.findByFolloweeId(userId1)).thenReturn(user1.getFollowers().stream());
        List<SubscriptionUserDto> result = subscriptionServiceImpl.getFollowers(userId1, new SubscriptionUserFilterDto());
        assertEquals(result.get(0).getId(), userId2);
        verify(searchAppearanceEventPublisher, times(1)).publish(any(SearchAppearanceEvent.class));
    }

    @Test
    public void testGetFollowersCount() {
        when(subscriptionRepository.findFollowersAmountByFolloweeId(userId1)).thenReturn(100);
        subscriptionServiceImpl.getFollowersCount(userId1);
        verify(subscriptionRepository, times(1)).findFollowersAmountByFolloweeId(userId1);
        assertEquals(100, subscriptionServiceImpl.getFollowersCount(userId1));
    }

    @Test
    public void testGetFollowing() {
        User user1 = new User();
        user1.setId(userId1);
        user1.setFollowees(List.of(User.builder().id(userId2).build()));
        when(subscriptionRepository.findByFolloweeId(userId1)).thenReturn(user1.getFollowees().stream());
        List<SubscriptionUserDto> result = subscriptionServiceImpl.getFollowing(userId1, new SubscriptionUserFilterDto());
        assertEquals(result.get(0).getId(), userId2);
        verify(searchAppearanceEventPublisher, times(1)).publish(any(SearchAppearanceEvent.class));
    }

    @Test
    public void testGetFollowingCount() {
        subscriptionServiceImpl.getFollowingCount(userId1);
        when(subscriptionRepository.findFolloweesAmountByFollowerId(userId1)).thenReturn(500);
        verify(subscriptionRepository, times(1)).findFolloweesAmountByFollowerId(userId1);
        assertEquals(500, subscriptionServiceImpl.getFollowingCount(userId1));
    }
}