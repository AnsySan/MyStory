package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.dto.SavingsAccountDto;
import com.clone.twitter.accountservice.dto.TariffDto;
import com.clone.twitter.accountservice.mapper.AccountMapper;
import com.clone.twitter.accountservice.model.*;
import com.clone.twitter.accountservice.repository.SavingAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Transactional
public class SavingsAccountService {
    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final SavingAccountRepository savingAccountRepository;
    private final TariffService tariffService;
    private final Executor executor;
    private final FreeAccountNumbersService freeAccountNumbersService;

    public SavingsAccountDto openAccount(SavingsAccountDto accountDTO) {
        Account account = createAccount(accountDTO);
        account = accountService.openAccount(account);
        Tariff tariff = tariffService.getTariffById(accountDTO.getCurrentTariff());

        SavingAccount savingAccount = accountMapper.toEntity(accountDTO);
        savingAccount.setAccount(account);
        savingAccount.setCurrentTariff(tariff);
        SavingAccount savedAccount = savingAccountRepository.save(savingAccount);

        SavingsAccountDto dto = accountMapper.toDto(savedAccount);
        fillDto(dto, account, accountMapper.toDto(savingAccount.getCurrentTariff()));
        return dto;
    }

    public SavingsAccountDto getSavingAccount(long accountId) {
        var savingAccount = savingAccountRepository.findById(accountId).orElseThrow(
                () -> new RuntimeException("Account with id: " + accountId + " wasn`t found")
        );
        var account = accountService.getAccountById(savingAccount.getAccount().getId());
        var tariffDto = accountMapper.toDto(savingAccount.getCurrentTariff());

        var accountDto = accountMapper.toDto(savingAccount);
        fillDto(accountDto, account, accountMapper.toDto(savingAccount.getCurrentTariff()));
        return accountDto;
    }

    public void updateInterest(int batchSize) {
        var accounts = savingAccountRepository.findAll();
        for (int i = 0; i < accounts.size(); i++) {
            var accountsBatch = accounts.subList(i, Math.min(accounts.size(), i + batchSize));
            executor.execute(() -> {
                accountsBatch.forEach(account -> {
                    BigDecimal currentRate = account.getCurrentTariff().getCurrentRate();
                    BigDecimal newBalance = account.getBalance().add(account.getBalance().multiply(currentRate));
                    account.setBalance(newBalance);
                });
            });
        }
    }

    public Account createAccount(SavingsAccountDto dto) {
        Account account = Account.builder()
                .type(AccountType.SAVINGS_ACCOUNT)
                .currency(dto.getCurrency())
                .userId(dto.getUserId())
                .status(AccountStatus.ACTIVE)
                .build();
        freeAccountNumbersService.perform(AccountType.SAVINGS_ACCOUNT, account::setNumber);
        return account;
    }

    private void fillDto(SavingsAccountDto dto, Account account, TariffDto tariffDto) {
        dto.setUserId(account.getUserId());
        dto.setCurrency(account.getCurrency());
        dto.setTariffDto(tariffDto);
    }
}