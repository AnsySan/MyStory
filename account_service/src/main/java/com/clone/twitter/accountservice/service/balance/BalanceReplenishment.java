package com.clone.twitter.accountservice.service.balance;

import com.clone.twitter.accountservice.dto.BalanceUpdateDto;
import com.clone.twitter.accountservice.dto.BalanceUpdateType;
import com.clone.twitter.accountservice.model.Balance;
import org.springframework.stereotype.Component;

@Component
public class BalanceReplenishment implements BalanceUpdater {
    @Override
    public boolean isApplicable(BalanceUpdateDto dto) {
        return dto.type() == BalanceUpdateType.REPLENISHMENT;
    }

    @Override
    public Balance update(Balance balance, BalanceUpdateDto dto) {
        return updateAuthAndActualBalance(balance, dto);
    }

    private Balance updateAuthAndActualBalance(Balance balance, BalanceUpdateDto dto) {
        balance.setAuthBalance(balance.getAuthBalance().add(dto.amount()));
        balance.setActualBalance(balance.getActualBalance().add(dto.amount()));
        return balance;
    }
}