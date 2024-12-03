package com.clone.twitter.payment_service.repository;

import com.clone.twitter.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);

    @Query(value = "SELECT p FROM Payment p WHERE p.scheduled_at IS NOT NULL AND p.status = 'AUTHORIZATION'",
            nativeQuery = true)
    List<Payment> findAllScheduledPayments();
}