package com.clone.twitter.userservice.controller.user;

import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.premium.PremiumPeriodDto;
import com.clone.twitter.userservice.service.user.premium.PremiumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("premium")
@RequiredArgsConstructor
@Tag(name = "Premium controller")
public class PremiumController {

    private final PremiumService premiumService;
    private final UserContext userContext;

    @Operation(summary = "Buy premium")
    @PostMapping
    public void buyPremium(@PathParam("premiumPeriod") PremiumPeriodDto premiumPeriod) {
        premiumService.buyPremium(userContext.getUserId(), premiumPeriod);
    }
}