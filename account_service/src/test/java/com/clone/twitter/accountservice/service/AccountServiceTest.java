package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.client.UserServiceClient;
import com.clone.twitter.accountservice.dto.account.AccountDto;
import com.clone.twitter.accountservice.exception.DataValidationException;
import com.clone.twitter.accountservice.mapper.AccountMapperImpl;
import com.clone.twitter.accountservice.model.Account;
import com.clone.twitter.accountservice.model.AccountStatus;
import com.clone.twitter.accountservice.model.AccountType;
import com.clone.twitter.accountservice.model.Currency;
import com.clone.twitter.accountservice.repository.AccountRepository;
import com.clone.twitter.accountservice.validator.AccountOwnerChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private AccountMapperImpl accountMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @Spy
    private AccountOwnerChecker accountOwnerChecker = new AccountOwnerChecker(userServiceClient);

    @InjectMocks
    private AccountService accountService;

    @Test
    void get_AccountFound_ShouldReturnCorrectDto() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(mockAccount()));

        Assertions.assertEquals(mockAccountDto(), accountService.get(1L));
    }

    @Test
    void create_RequestHasNoOwners_ShouldThrowException() {
        AccountDto accountDto = mockAccountDto();
        accountDto.setUserId(null);

        DataValidationException e = Assertions.assertThrows(DataValidationException.class, () -> {
            accountService.create(accountDto);
        });
        Assertions.assertEquals("Post's author can be only author or project and can't be both", e.getMessage());
    }

    @Test
    void create_RequestHasOnlyOneOwner_ShouldMapCorrectlyAndSave() {
        Mockito.doNothing().when(accountOwnerChecker).validateToCreate(mockAccountDto());

        accountService.create(mockAccountDto());

        Mockito.verify(accountRepository).save(mockAccount());
    }

    private Account mockAccount() {
        return Account.builder()
                .id(1L)
                .userId(1L)
                .number("123456789012345")
                .status(AccountStatus.ACTIVE)
                .type(AccountType.CURRENT_ACCOUNT)
                .currency(Currency.USD)
                .version(1L)
                .build();
    }

    private AccountDto mockAccountDto() {
        return AccountDto.builder()
                .id(1L)
                .userId(1L)
                .number("123456789012345")
                .status(AccountStatus.ACTIVE)
                .type(AccountType.CURRENT_ACCOUNT)
                .currency(Currency.USD)
                .version(1L)
                .build();
    }
}