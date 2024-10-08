package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.profile.ProfileViewEventDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.mapper.user.UserMapperImpl;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.model.user.UserProfilePicture;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.exception.MessageError;
import com.clone.twitter.userservice.exception.UserNotFoundException;
import com.clone.twitter.userservice.publisher.ProfileViewEventPublisher;
import com.clone.twitter.userservice.repository.UserRepository;
import com.clone.twitter.userservice.service.cloud.S3Service;
import com.clone.twitter.userservice.service.user.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;

    @Mock
    UserMapperImpl userMapper;
    @Mock
    private UserContext userContext;
    @Mock
    private S3Service s3Service;
    @Mock
    private ProfileViewEventPublisher profileViewEventPublisher;

    User firstUser;
    User secondUser;
    List<Long> userIds;
    List<User> users;
    ProfileViewEventDto eventDto;

    @BeforeEach
    void setUp() {
        firstUser = User.builder()
                .id(1L)
                .username("Petya")
                .build();
        secondUser = User.builder()
                .id(2L)
                .username("Vanya")
                .build();
        userIds = List.of(firstUser.getId(), firstUser.getId());
        users = List.of(firstUser, secondUser);

        eventDto = ProfileViewEventDto.builder()
                .build();
    }

    @Test
    public void testGetUser_UserDoesNotExist() {
        when(userRepository.findById(firstUser.getId())).thenReturn(Optional.empty());

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUser(firstUser.getId()));
        assertEquals(e.getMessage(), MessageError.USER_NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    public void testGetUser() {
        when(userRepository.findById(firstUser.getId())).thenReturn(Optional.ofNullable(firstUser));
        when(userContext.getUserId()).thenReturn(secondUser.getId());

        userServiceImpl.getUser(firstUser.getId());

        verify(userRepository, times(1)).findById(firstUser.getId());
        verify(userMapper, times(1)).toDto(firstUser);
        verify(profileViewEventPublisher, times(1)).publish(any(ProfileViewEventDto.class));
    }

    @Test
    public void testGetUsers() {
        when(userRepository.findAllById(userIds)).thenReturn(users);

        userServiceImpl.getUsersByIds(userIds);

        verify(userRepository, times(1)).findAllById(userIds);
        verify(userMapper, times(1)).toDto(users);
    }

    @Test
    public void testDeactivateUser() {
        long id = 1L;
        User user = User.builder().id(id).active(true).build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userServiceImpl.deactivate(id);
        assertFalse(user.isActive());
    }

    @Test
    public void testCreateSuccess() {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("John Doe");

        User user = new User();
        user.setId(1L);
        user.setUsername("John Doe");

        UserProfilePicture userProfilePic = UserProfilePicture.builder()
                .smallFileId("smallFileId")
                .fileId("fileId")
                .build();
        user.setUserProfilePic(userProfilePic);

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        UserDto createdUserDto = userServiceImpl.create(userDto);

        assertNotNull(createdUserDto);
        assertEquals(userDto.getId(), createdUserDto.getId());
        assertEquals(userDto.getUsername(), createdUserDto.getUsername());

    }

    @Test
    public void testCreate_UserAlreadyExists_ExceptionThrown() {

        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> userServiceImpl.create(userDto));
        assertEquals("User with id 1 exists", exception.getMessage());

    }
}