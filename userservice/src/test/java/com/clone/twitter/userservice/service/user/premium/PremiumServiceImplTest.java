package com.clone.twitter.userservice.service.user.premium;

import com.clone.twitter.userservice.client.PaymentServiceClient;
import com.clone.twitter.userservice.dto.payment.Currency;
import com.clone.twitter.userservice.dto.payment.PaymentRequest;
import com.clone.twitter.userservice.dto.payment.PaymentResponse;
import com.clone.twitter.userservice.dto.payment.PaymentStatus;
import com.clone.twitter.userservice.dto.premium.PremiumPeriodDto;
import com.clone.twitter.userservice.exception.PaymentException;
import com.clone.twitter.userservice.mapper.premium.PremiumMapper;
import com.clone.twitter.userservice.repository.premium.PremiumRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PremiumServiceImplTest {

    @Mock
    private PremiumRepository premiumRepository;
    @Spy
    private PremiumMapper premiumMapper = Mappers.getMapper(PremiumMapper.class);
    @Mock
    private PaymentServiceClient paymentServiceClient;

    @InjectMocks
    private PremiumServiceImpl premiumServiceImpl;

    @Test
    void successBuyPremium() {
        PremiumPeriodDto premiumPeriod = PremiumPeriodDto.MONTH;
        Long userId = 1L;
        PaymentRequest paymentRequest = new PaymentRequest(1, premiumPeriod.getPrice(), Currency.USD);

        when(paymentServiceClient.sendPayment(paymentRequest)).thenReturn(ResponseEntity.ok(new PaymentResponse(
                PaymentStatus.SUCCESS,
                0,
                0,
                null,
                null,
                null
        )));

        when(premiumRepository.existsByUserId(userId)).thenReturn(false);

        premiumServiceImpl.buyPremium(userId, premiumPeriod);

        verify(premiumRepository).save(any());
    }

    @Test
    void buyPremiumThrowException() {
        PremiumPeriodDto premiumPeriod = PremiumPeriodDto.MONTH;
        Long userId = 1L;
        PaymentRequest paymentRequest = new PaymentRequest(1, premiumPeriod.getPrice(), Currency.USD);

        when(paymentServiceClient.sendPayment(paymentRequest)).thenReturn(ResponseEntity.ok(new PaymentResponse(
                PaymentStatus.FAILURE,
                0,
                0,
                null,
                null,
                null
        )));

        assertThrows(PaymentException.class, () -> premiumServiceImpl.buyPremium(userId, premiumPeriod));
    }
}
