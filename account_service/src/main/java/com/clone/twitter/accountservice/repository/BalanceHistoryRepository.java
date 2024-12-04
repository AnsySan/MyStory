package com.clone.twitter.accountservice.repository;

import com.clone.twitter.accountservice.model.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}