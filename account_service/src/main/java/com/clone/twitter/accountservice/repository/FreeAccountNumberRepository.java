package com.clone.twitter.accountservice.repository;

import com.clone.twitter.accountservice.model.AccountType;
import com.clone.twitter.accountservice.model.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAccountNumberRepository extends JpaRepository<FreeAccountNumber, Long> {

    Optional<FreeAccountNumber> findByAccountType(AccountType accountType);

    @Query(nativeQuery = true, value = """
        DELETE FROM free_account_numbers
        WHERE account_type = :accountType
        AND account_number = (
            SELECT account_number
            FROM free_account_numbers
            WHERE account_type = :accountType
            ORDER BY account_number
            LIMIT 1
        )
        RETURNING account_number
        """)
    Optional<Long> getAccountNumber(int accountType);
}