package com.clone.twitter.accountservice.service.balance;

import com.clone.twitter.accountservice.dto.BalanceUpdateDto;
import com.clone.twitter.accountservice.dto.BalanceUpdateType;
import com.clone.twitter.accountservice.model.Balance;
import org.springframework.stereotype.Component;

@Component
public class BalanceAuthorization implements BalanceUpdater {
    @Override
    public boolean isApplicable(BalanceUpdateDto dto) {
        return dto.type() == BalanceUpdateType.AUTHORIZATION;
    }

    @Override
    public Balance update(Balance balance, BalanceUpdateDto dto) {
        balance.setAuthBalance(
                balance.getAuthBalance()
                        .subtract(dto.amount()));
        return balance;
    }
}