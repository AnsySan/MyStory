package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.dto.account.AccountDto;
import com.clone.twitter.accountservice.mapper.AccountMapper;
import com.clone.twitter.accountservice.model.Account;
import com.clone.twitter.accountservice.repository.AccountRepository;
import com.clone.twitter.accountservice.validator.AccountOwnerChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountOwnerChecker accountOwnerChecker;

    public AccountDto get(Long id) {
        Account foundAccount = getAccountById(id);

        return accountMapper.toDto(foundAccount);
    }

    @Transactional
    public AccountDto create(AccountDto dto) {
        accountOwnerChecker.validateToCreate(dto);

        Account account = accountMapper.toEntity(dto);

        Account saved = accountRepository.save(account);

        return accountMapper.toDto(saved);
    }

    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No account with id: " + id)
        );
    }
    public Account openAccount(Account account) {
        return accountRepository.save(account);
    }
}