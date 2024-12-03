package com.clone.twitter.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "account-service", url = "${account-service.host}:${account-service.port}")
public class AccountServiceClient {
}