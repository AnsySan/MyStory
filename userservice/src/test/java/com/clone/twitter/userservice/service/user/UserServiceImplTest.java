package com.clone.twitter.userservice.service.user;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.user.UserMapper;
import com.clone.twitter.userservice.mapper.user.UserMapperImpl;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.premium.Premium;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.publisher.profile.ProfileViewEventPublisher;
import com.clone.twitter.userservice.repository.user.UserRepository;
import com.clone.twitter.userservice.service.event.EventService;
import com.clone.twitter.userservice.service.goal.GoalService;
import com.clone.twitter.userservice.service.user.filter.UserFilterService;
import com.clone.twitter.userservice.service.user.mentorship.MentorshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GoalService goalService;
    @Mock
    private EventService eventService;
    @Mock
    private MentorshipService mentorshipService;
    @Mock
    private UserFilterService userFilterService;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();
    @Captor
    private ArgumentCaptor<User> captor;
    private User user;

    @Mock
    private UserContext userContext;

    @Mock
    private ProfileViewEventPublisher profileViewEventPublisher;
    private UserDto userDto;

    private List<User> getUsers() {
        return new ArrayList<>(List.of(
                User.builder().id(1L).premium(new Premium()).build(),
                User.builder().id(2L).premium(new Premium()).build(),
                User.builder().id(3L).premium(new Premium()).build()
        ));
    }

    @BeforeEach
    public void setUp() {
        user = User.builder().username("name").email("test@mail.ru").password("password").build();
    }

    @Test
    void findPremiumUsers() {
        Stream<User> users = getUsers().stream();
        UserFilterDto userFilterDto = new UserFilterDto();

        when(userRepository.findPremiumUsers()).thenReturn(users);
        when(userFilterService.applyFilters(users, userFilterDto)).thenReturn(users);

        assertEquals(userServiceImpl.findPremiumUsers(userFilterDto), getUsers().stream().map(userMapper::toDto).toList());
    }

    @Test
    void deactivateUserById() {
        User deactivatedUser = User.builder().id(1L).build();
        User anotherUser = User.builder().id(2L).build();
        List<Goal> deactivatedUserGoals = getGoals(new ArrayList<>(List.of(deactivatedUser, anotherUser)));
        deactivatedUser.setGoals(deactivatedUserGoals);

        when(userRepository.findById(1L)).thenReturn(Optional.of(deactivatedUser));
        doNothing().when(eventService).deleteAll(any());
        doNothing().when(mentorshipService).deleteMentorFromMentee(any());
        when(userRepository.save(deactivatedUser)).thenReturn(deactivatedUser);

        userServiceImpl.deactivateUserById(1L);

        assertFalse(deactivatedUser.isActive());
        assertEquals(1, deactivatedUser.getGoals().get(0).getUsers().size());
        assertEquals(0, deactivatedUser.getGoals().get(1).getUsers().size());
        assertEquals(1, deactivatedUser.getGoals().get(2).getUsers().size());
    }

    private List<Goal> getGoals(List<User> users) {
        return new ArrayList<>(List.of(
                Goal.builder().id(1L).users(users).build(),
                Goal.builder().id(2L).users(new ArrayList<>(List.of(users.get(0)))).build(),
                Goal.builder().id(3L).users(users).build()
        ));
    }

    @Test
    public void testGetUser() {
        long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        userServiceImpl.findUserById(userId);
        verify(userRepository).findById(userId);
    }

    @Test
    public void testGetUsersByIds() {
        List<Long> ids = List.of(1L, 2L, 3L);
        Mockito.when(userRepository.findAllById(ids)).thenReturn(List.of(new User(), new User()));
        userServiceImpl.getUsersByIds(ids);
        verify(userRepository).findAllById(ids);
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto userDto = UserDto.builder().username("name").email("test@mail.ru").password("password").build();
        UserDto result = userServiceImpl.createUser(userDto);

        InOrder inOrder = inOrder(userMapper, userRepository);
        inOrder.verify(userMapper, times(1)).toEntity(userDto);
        inOrder.verify(userRepository, times(1)).save(captor.capture());
        inOrder.verify(userMapper, times(1)).toDto(user);
        assertTrue(captor.getValue().isActive());
        assertEquals(result.getUsername(), userDto.getUsername());
        assertEquals(result.getEmail(), userDto.getEmail());
        assertEquals(result.getPassword(), userDto.getPassword());
    }

    @Test
    public void testGetUserById_Success() {
        user = new User();
        user.setId(1L);

        userDto = new UserDto();
        userDto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userServiceImpl.getUserById(1L);

        assertEquals(userDto, result);
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        user = new User();
        user.setId(1L);

        userDto = new UserDto();
        userDto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userServiceImpl.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(userMapper, userContext, profileViewEventPublisher);
    }
}
