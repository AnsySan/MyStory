package com.clone.twitter.user_service.repository.country;

import com.clone.twitter.user_service.model.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM country
            WHERE title = :name
            """)
    Optional<Country> findByName(String name);
}