package com.clone.twitter.payment_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto {
    private Long id;
    private String senderAccount;
    private String receiverAccount;
    private Currency currency;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
}