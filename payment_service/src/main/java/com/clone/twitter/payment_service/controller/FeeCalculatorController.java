package com.clone.twitter.payment_service.controller;

import com.clone.twitter.payment_service.service.FeeCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/payments")
public class FeeCalculatorController {

    private final FeeCalculatorService feeCalculatorService;

    @GetMapping
    public BigDecimal calculateFee(BigDecimal convertedAmount, double percent){
        return feeCalculatorService.calculateFee(convertedAmount, percent);
    }
}
