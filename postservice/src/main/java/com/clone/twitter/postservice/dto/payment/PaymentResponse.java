package com.clone.twitter.postservice.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponse {
    @NotNull
   private PaymentStatus status;
    @NotNull
   private int verificationCode;
    @NotNull
   private long paymentNumber;
    @NotNull
   private BigDecimal amount;
    @NotNull
   private Currency currency;
    @NotNull
   private String message;
}
