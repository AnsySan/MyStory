package com.clone.twitter.userservice.service.user.premium;

import com.clone.twitter.userservice.client.PaymentServiceClient;
import com.clone.twitter.userservice.dto.payment.Currency;
import com.clone.twitter.userservice.dto.payment.PaymentRequest;
import com.clone.twitter.userservice.dto.payment.PaymentResponse;
import com.clone.twitter.userservice.dto.payment.PaymentStatus;
import com.clone.twitter.userservice.dto.premium.PremiumPeriodDto;
import com.clone.twitter.userservice.event.premium.PremiumBoughtEvent;
import com.clone.twitter.userservice.exception.PaymentException;
import com.clone.twitter.userservice.mapper.premium.PremiumMapper;
import com.clone.twitter.userservice.model.premium.Premium;
import com.clone.twitter.userservice.publisher.premium.PremiumBoughtEventPublisher;
import com.clone.twitter.userservice.repository.premium.PremiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PremiumServiceImpl implements PremiumService {

    private final PremiumRepository premiumRepository;
    private final PremiumMapper premiumMapper;
    private final PaymentServiceClient paymentServiceClient;
    private final PremiumBoughtEventPublisher premiumBoughtEventPublisher;

    @Override
    @Transactional
    public void buyPremium(Long userId, PremiumPeriodDto premiumPeriod) {

        ResponseEntity<PaymentResponse> paymentResult = paymentServiceClient.sendPayment(new PaymentRequest(1, premiumPeriod.getPrice(), Currency.USD));

        if (!paymentResult.getStatusCode().equals(HttpStatusCode.valueOf(200))
                || !paymentResult.getBody().status().equals(PaymentStatus.SUCCESS)
        ) {
            throw new PaymentException("Payment declined");
        }

        if (!premiumRepository.existsByUserId(userId)) {
            Premium premium = premiumMapper.toEntity(userId, LocalDateTime.now(), LocalDateTime.now().plusDays(premiumPeriod.getDays()));
            premiumRepository.save(premium);
            return;
        }

        premiumRepository.findByUserId(userId)
                .ifPresentOrElse(
                        premium -> {
                            premium.setEndDate(premium.getEndDate().plusDays(premiumPeriod.getDays()));
                            premiumRepository.save(premium);
                        },
                        () -> {
                            Premium premium = premiumMapper.toEntity(userId, LocalDateTime.now(), LocalDateTime.now().plusDays(premiumPeriod.getDays()));
                            premiumRepository.save(premium);
                        }
                );

        PremiumBoughtEvent event = PremiumBoughtEvent.builder()
                .userId(userId)
                .amount(premiumPeriod.getPrice())
                .premiumPeriod(premiumPeriod.getDays())
                .boughtAt(LocalDateTime.now())
                .build();
        premiumBoughtEventPublisher.publish(event);
    }

    @Override
    @Transactional
    public void deleteAllExpiredPremium() {
        List<Long> expiredIds = premiumRepository.findAllExpiredIds();
        premiumRepository.deleteAllById(expiredIds);
    }
}