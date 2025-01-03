package com.clone.twitter.user_service.client;

import com.clone.twitter.user_service.dto.payment.PaymentRequest;
import com.clone.twitter.user_service.dto.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${payment-service.host}:${payment-service.port}")
public interface PaymentServiceClient {
    @PostMapping("api/payment")
    ResponseEntity<PaymentResponse> sendPayment(@RequestBody PaymentRequest paymentRequest);
}
