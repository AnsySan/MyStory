package com.clone.twitter.accountservice.controller;

import com.clone.twitter.accountservice.dto.account.AccountDto;
import com.clone.twitter.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountDto get(@PathVariable(value = "id") Long id) {
        AccountDto result = accountService.get(id);

        return result;
    }

    @PostMapping("/")
    public AccountDto create(@Valid @RequestBody AccountDto accountDto) {
        AccountDto result = accountService.create(accountDto);

        return result;
    }
}