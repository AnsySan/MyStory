package com.clone.twitter.userservice.service.user.premium;

import com.clone.twitter.userservice.dto.premium.PremiumPeriodDto;

import java.util.List;

public interface PremiumService {

    void buyPremium(Long userId, PremiumPeriodDto premiumPeriod);

    void deleteAllExpiredPremium();
}