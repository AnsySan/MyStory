package com.clone.twitter.payment_service.client;

import com.clone.twitter.payment_service.model.ExchangeRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "openExchangeRatesClient", url = "${feign.openExchangeRatesClient.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/latest.json?app_id=${feign.openExchangeRatesClient.appId}")
    ExchangeRatesResponse getRates();
}