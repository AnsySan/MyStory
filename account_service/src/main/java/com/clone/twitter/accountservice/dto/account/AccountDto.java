package com.clone.twitter.accountservice.dto.account;

import com.clone.twitter.accountservice.model.AccountStatus;
import com.clone.twitter.accountservice.model.AccountType;
import com.clone.twitter.accountservice.model.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String number;

    private Long userId;

    @NotNull(message = "Select account type")
    private AccountType type;

    @NotNull(message = "Select currency")
    private Currency currency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AccountStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant closedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long version;
}