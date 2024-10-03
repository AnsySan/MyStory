package com.clone.twitter.payment_service.service;

import com.clone.twitter.payment_service.dto.PaymentRequest;
import com.clone.twitter.payment_service.dto.PaymentResponse;
import com.clone.twitter.payment_service.dto.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final CurrencyConverterService currencyConverterService;

    public ResponseEntity<PaymentResponse> sendPayment(PaymentRequest dto) {
        BigDecimal convertedAmount = currencyConverterService.convertCurrencyToUSD(dto);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedSum = decimalFormat.format(convertedAmount);
        int verificationCode = new Random().nextInt(1000, 10000);
        String message = String.format("Dear friend! Thank you for your purchase! " +
                        "Your payment on %s %s was accepted.",
                formattedSum, dto.currency().name());


        return ResponseEntity.ok(new PaymentResponse(
                PaymentStatus.SUCCESS,
                verificationCode,
                dto.paymentNumber(),
                convertedAmount,
                dto.currency(),
                message)
        );
    }
}