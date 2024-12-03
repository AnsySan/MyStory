package com.clone.twitter.payment_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class InvoiceDto {

    @NotNull(message = "sender is required")
    @NotEmpty(message = "sender is required")
    private String senderAccount;

    @NotNull(message = "receiver is required")
    @NotEmpty(message = "receiver is required")
    private String receiverAccount;

    @NotNull(message = "currency is required")
    private Currency currency;

    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotNull(message = "idempotencyKey is required")
    private UUID idempotencyKey;
}