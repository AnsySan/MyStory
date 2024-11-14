package com.clone.twitter.postservice.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotNull
    private long paymentNumber;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
}
