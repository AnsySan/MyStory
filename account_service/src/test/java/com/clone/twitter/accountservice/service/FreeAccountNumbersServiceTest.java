package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.model.AccountType;
import com.clone.twitter.accountservice.repository.FreeAccountNumberRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
public class FreeAccountNumbersServiceTest {
    @Autowired
    FreeAccountNumbersService freeAccountNumbersService;
    @Autowired
    FreeAccountNumberRepository freeAccountNumberRepository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:13.3"
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

    @Test
    void GenerateAccountNumberTest() {
        freeAccountNumbersService.generateAccountNumber(AccountType.CURRENT_ACCOUNT);
        var number = freeAccountNumberRepository.findByAccountType(AccountType.CURRENT_ACCOUNT);
        Assertions.assertTrue(number.isPresent());
    }
}