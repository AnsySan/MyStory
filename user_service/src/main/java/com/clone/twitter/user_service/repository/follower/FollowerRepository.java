package com.clone.twitter.user_service.repository.follower;

import com.clone.twitter.user_service.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<User, Long> {
}