package com.clone.twitter.userservice.repository;

import com.clone.twitter.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<User, Long> {
}