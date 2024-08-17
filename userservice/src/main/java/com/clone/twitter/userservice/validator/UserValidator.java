package com.clone.twitter.userservice.validator;

import com.clone.twitter.userservice.exception.MessageError;
import com.clone.twitter.userservice.exception.UserNotFoundException;
import com.clone.twitter.userservice.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void userExistenceInRepo(long userId){
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND_EXCEPTION);
        }
    }
}