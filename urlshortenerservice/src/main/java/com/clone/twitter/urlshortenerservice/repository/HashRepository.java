package com.clone.twitter.urlshortenerservice.repository;

import com.clone.twitter.urlshortenerservice.entity.Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashRepository extends JpaRepository<Hash, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT nextval(unique_number_sequence) FROM generate_series(1, :batchSize)")
    List<Long> getFollowingRangeUniqueNumbers(int batchSize);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM hash WHERE hash IN (SELECT hash FROM hash LIMIT :batch) RETURNING *")
    List<Hash> getHashBatch(int batch);

}