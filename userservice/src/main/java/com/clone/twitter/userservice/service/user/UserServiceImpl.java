package com.clone.twitter.userservice.service.user;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.user.UserMapper;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.user.UserRepository;
import com.clone.twitter.userservice.service.event.EventService;
import com.clone.twitter.userservice.service.goal.GoalService;
import com.clone.twitter.userservice.service.user.filter.UserFilterService;
import com.clone.twitter.userservice.service.user.mentorship.MentorshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFilterService userFilterService;
    private final UserMapper userMapper;
    private final GoalService goalService;
    private final EventService eventService;
    private final MentorshipService mentorshipService;

    @Override
    @Transactional
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public List<UserDto> findPremiumUsers(UserFilterDto filterDto) {
        return userFilterService.applyFilters(userRepository.findPremiumUsers(), filterDto)
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deactivateUserById(Long id) {
        User user = findUserById(id);
        List<Goal> userGoals = user.getGoals();

        userGoals.forEach(goal -> {
            List<User> goalUsers = goal.getUsers();
            goalUsers.remove(user);
            if (goalUsers.isEmpty()) {
                goalService.delete(goal);
            }
        });
        eventService.deleteAll(user.getOwnedEvents());
        mentorshipService.deleteMentorFromMentee(user);

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto getUserById(long userId) {
        User user = findUserById(userId);
        return userMapper.toDto(user);
    }

    @Override
    public void banUserByIds(List<Long> userIds) {
        userRepository.findAllById(userIds).stream()
                .peek(user -> user.setIsBanned(true))
                .forEach(userRepository::save);
    }

    @Override
    @Transactional
    public UserDto createUser(@Valid UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setActive(true);
        User saved = userRepository.save(user);
        log.info("Created new user {}", saved.getId());
        return userMapper.toDto(saved);
    }
}