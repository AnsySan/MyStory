package com.clone.twitter.payment_service.controller;

import com.clone.twitter.payment_service.dto.PaymentRequest;
import com.clone.twitter.payment_service.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Validated
public class ConverterController {

   private final CurrencyConverterService currencyConverterService;

   @PostMapping
   public BigDecimal convertCurrencyToUSD(PaymentRequest paymentRequest) {
      return currencyConverterService.convertCurrencyToUSD(paymentRequest);
   }
}