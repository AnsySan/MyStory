package com.clone.twitter.accountservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
    CURRENT_ACCOUNT("5200"),
    SAVINGS_ACCOUNT("5236"),
    CORPORATE_ACCOUNT("5300"),
    PERSONAL_ACCOUNT("5400"),
    JOINT_ACCOUNT("5500"),
    FOREIGN_CURRENCY_ACCOUNT("5600"),
    INVESTMENT_ACCOUNT("5700"),
    MERCHANT_ACCOUNT("5800"),
    TRUST_ACCOUNT("5900"),
    ESCROW_ACCOUNT("6000");

    private final String associatedString;
}