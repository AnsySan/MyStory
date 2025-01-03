package com.clone.twitter.user_service.repository.premium;

import com.clone.twitter.user_service.model.premium.Premium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumRepository extends CrudRepository<Premium, Long> {

    Optional<Premium> findByUserId(Long userId);

    boolean existsByUserId(long userId);

    List<Premium> findAllByEndDateBefore(LocalDateTime endDate);

    @Query(value = """
            SELECT p.id FROM Premium p
            WHERE p.endDate < CURRENT_TIMESTAMP
            """)
    List<Long> findAllExpiredIds();
}