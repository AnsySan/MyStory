package com.clone.twitter.payment_service.model;

import com.clone.twitter.payment_service.dto.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRatesResponse {
    @JsonIgnore
    private String disclaimer;
    @JsonIgnore
    private String license;

    private long timestamp;

    private Currency base;

    private Map<String, BigDecimal> rates;
}