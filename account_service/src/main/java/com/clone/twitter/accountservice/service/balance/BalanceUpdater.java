package com.clone.twitter.accountservice.service.balance;

import com.clone.twitter.accountservice.dto.BalanceUpdateDto;
import com.clone.twitter.accountservice.model.Balance;

public interface BalanceUpdater {

    boolean isApplicable(BalanceUpdateDto dto);

    Balance update(Balance balance, BalanceUpdateDto dto);
}