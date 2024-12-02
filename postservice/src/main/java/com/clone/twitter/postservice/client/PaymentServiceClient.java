package com.clone.twitter.postservice.client;

import com.clone.twitter.postservice.dto.payment.PaymentRequest;
import com.clone.twitter.postservice.dto.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment_service", url = "${payment-service.host}:${payment-service.port}")
public interface PaymentServiceClient {

    @PostMapping("/api/payment")
    ResponseEntity<PaymentResponse> postPayment(@RequestBody PaymentRequest paymentRequest);
}
