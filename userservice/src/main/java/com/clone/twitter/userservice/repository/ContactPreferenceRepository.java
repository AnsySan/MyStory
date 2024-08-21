package com.clone.twitter.userservice.repository;

import com.clone.twitter.userservice.entity.ContactPreference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPreferenceRepository extends CrudRepository<ContactPreference, Long> {

    Optional<ContactPreference> findByUserUsername(String username);

}