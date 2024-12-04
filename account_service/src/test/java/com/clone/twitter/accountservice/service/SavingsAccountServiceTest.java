package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.dto.SavingsAccountDto;
import com.clone.twitter.accountservice.model.Currency;
import com.clone.twitter.accountservice.model.SavingAccount;
import com.clone.twitter.accountservice.model.Tariff;
import com.clone.twitter.accountservice.model.TariffType;
import com.clone.twitter.accountservice.repository.AccountRepository;
import com.clone.twitter.accountservice.repository.SavingAccountRepository;
import com.clone.twitter.accountservice.repository.TariffRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.Objects;

@SpringBootTest
public class SavingsAccountServiceTest {
    @Autowired
    SavingsAccountService savingsAccountService;
    @Autowired
    SavingAccountRepository savingAccountRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TariffRepository tariffRepository;

    Tariff tariff;
    SavingsAccountDto savingsAccountDto;
    SavingAccount savingAccount;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        tariff = Tariff.builder()
                .tariffType(TariffType.GOLD)
                .currentRate(BigDecimal.valueOf(12))
                .build();
        tariff = tariffRepository.save(tariff);
        savingAccount = SavingAccount.builder()
                .balance(new BigDecimal(1000))
                .currentTariff(tariff)
                .build();

        savingsAccountDto = SavingsAccountDto.builder()
                .balance(new BigDecimal(1000))
                .currentTariff(tariff.getId())
                .currency(Currency.USD)
                .userId(1L)
                .build();
    }

    @Test
    void openAccountTest() {
        var savingAccount = savingsAccountService.openAccount(savingsAccountDto);
        Assertions.assertNotNull(savingAccountRepository.findById(savingAccount.getId()).get());
    }

    @Test
    void getSavingAccountTest() {
        var account = savingsAccountService.openAccount(savingsAccountDto);
        var res = savingsAccountService.getSavingAccount(account.getId());
        Assertions.assertEquals(res.getTariffDto().getCurrentRate(), tariff.getCurrentRate());
    }

    @Test
    void updateInterestTest() {
        var account = savingsAccountService.openAccount(savingsAccountDto);
        savingsAccountService.updateInterest(1);
        var res = savingAccountRepository.findById(account.getId());
        Assertions.assertTrue(!Objects.equals(res.get().getBalance(), account.getBalance()));
    }
}