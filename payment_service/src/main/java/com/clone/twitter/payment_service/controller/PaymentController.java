package com.clone.twitter.payment_service.controller;

import com.clone.twitter.payment_service.dto.PaymentRequest;
import com.clone.twitter.payment_service.dto.PaymentResponse;
import com.clone.twitter.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> sendPayment(PaymentRequest paymentRequest){
        return paymentService.sendPayment(paymentRequest);
    }
}
