package com.clone.instagram.authSetvice.Repository;

import com.clone.instagram.authSetvice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findById(String id);
    List<User> findByUsernameIn(List<String> usernames);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
