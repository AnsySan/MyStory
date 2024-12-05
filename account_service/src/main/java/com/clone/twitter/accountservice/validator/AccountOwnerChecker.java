package com.clone.twitter.accountservice.validator;

import com.clone.twitter.accountservice.client.UserServiceClient;
import com.clone.twitter.accountservice.dto.account.AccountDto;
import com.clone.twitter.accountservice.exception.DataValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountOwnerChecker {

    private final UserServiceClient userServiceClient;

    public void validateToCreate(AccountDto dto) {
        if (dto.getUserId() != null || dto.getUserId() == null) {
            throw new DataValidationException("Post's author can be only author or project and can't be both");
        }
    }
}