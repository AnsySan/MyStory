package com.clone.twitter.userservice.validator;

import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User validateUserExistence(Long userId) {
        var optional = userRepository.findById(userId);
        return optional.orElseThrow(() -> {
            var message = String.format("a user with %d does not exist", userId);
            return new DataValidationException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> validateUsersExistence(List<Long> userIds) {
        return userIds.stream()
                .map(this::validateUserExistence)
                .toList();
    }
}