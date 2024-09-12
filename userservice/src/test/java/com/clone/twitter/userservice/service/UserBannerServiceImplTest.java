package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.service.user.UserBannerServiceImpl;
import com.clone.twitter.userservice.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserBannerServiceImplTest {
    @Mock
    UserServiceImpl userServiceImpl;
    @InjectMocks
    UserBannerServiceImpl userBannerServiceImpl;
    User firstUser;
    List<Long> userIdsToBan;
    List<User> usersToBan;
    @BeforeEach
    void setUp(){

        firstUser = User.builder()
                .id(1)
                .banned(false)
                .build();
        userIdsToBan = List.of(firstUser.getId());
        usersToBan = List.of(firstUser);

    }

    @Test
    public void testBanUserById(){
        Mockito.when(userServiceImpl.getUserEntityById(firstUser.getId())).thenReturn(firstUser);
        userBannerServiceImpl.banUserById(firstUser.getId());
    }

    @Test
    public void testBanUsersByIds(){
        Mockito.when(userServiceImpl.getUsersEntityByIds(userIdsToBan)).thenReturn(usersToBan);
        userBannerServiceImpl.banUsersByIds(userIdsToBan);
    }
}