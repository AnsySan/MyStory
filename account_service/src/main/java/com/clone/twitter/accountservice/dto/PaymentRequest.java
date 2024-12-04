package com.clone.twitter.accountservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Currency;

public record PaymentRequest(
        @NotNull
        long paymentNumber,

        @Min(1)
        @NotNull
        BigDecimal amount,

        @NotNull
        Currency currency
){
}