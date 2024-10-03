package com.clone.twitter.payment_service.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@NoArgsConstructor
public class FeeCalculatorService {
    public BigDecimal calculateFee(BigDecimal convertedAmount, double percent) {
        BigDecimal decimalPercent = BigDecimal.valueOf(percent / 100);
        return convertedAmount.multiply(decimalPercent);
    }
}