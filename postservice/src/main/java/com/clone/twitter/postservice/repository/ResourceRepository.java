package com.clone.twitter.postservice.repository;

import com.clone.twitter.postservice.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    boolean existsByKey(String key);

    Resource findByKey(String key);

    int countAllByPost_Id(int postId);

    void deleteByKey(String key);
}