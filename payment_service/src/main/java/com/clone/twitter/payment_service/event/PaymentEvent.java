package com.clone.twitter.payment_service.event;

import com.clone.twitter.payment_service.dto.Currency;
import com.clone.twitter.payment_service.dto.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentEvent {
    private String senderAccount;
    private String receiverAccount;
    private Currency currency;
    private BigDecimal amount;
    private PaymentStatus type;
}