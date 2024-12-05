package com.clone.twitter.accountservice.controller;

import com.clone.twitter.accountservice.dto.SavingsAccountDto;
import com.clone.twitter.accountservice.service.SavingsAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/savings-account")
public class SavingsAccountController {
    private final SavingsAccountService savingsAccountService;

    @PostMapping
    public ResponseEntity<SavingsAccountDto> openAccount(@RequestBody @Valid SavingsAccountDto savingsAccountDTO) {
        var accountDto = savingsAccountService.openAccount(savingsAccountDTO);
        return ResponseEntity.ok().body(accountDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SavingsAccountDto> getSavingAccount(@PathVariable long id) {
        var tariffDto = savingsAccountService.getSavingAccount(id);
        return ResponseEntity.ok().body(tariffDto);
    }
}