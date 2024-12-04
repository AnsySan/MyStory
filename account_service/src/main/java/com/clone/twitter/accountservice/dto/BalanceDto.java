package com.clone.twitter.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class BalanceDto {

    @NotNull
    private Long id;
    @NotNull
    private Long accountId;
    private BigDecimal authBalance;
    private BigDecimal actualBalance;
    private List<BalanceHistoryDto> history;
    private Instant createdAt;
    private Instant updatedAt;
}