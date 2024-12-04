package com.clone.twitter.accountservice.controller;

import com.clone.twitter.accountservice.context.UserContext;
import com.clone.twitter.accountservice.repository.AccountRepository;
import com.clone.twitter.accountservice.service.AccountService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Testcontainers
class AccountControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.1")
            .withInitScript("db/changelog/changeset/testcontainers/V001__account-service_get.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserContext userContext;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void get() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/account/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.number").value("123456789013123"))
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.type").value("CURRENT_ACCOUNT"))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.version").value("0"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}