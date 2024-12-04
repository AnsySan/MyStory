package com.clone.twitter.accountservice.dto;

import java.math.BigDecimal;
import java.util.Currency;

public record PaymentResponse(
        PaymentStatus status,
        int verificationCode,
        long paymentNumber,
        BigDecimal amount,
        Currency currency,
        String message
) {
}