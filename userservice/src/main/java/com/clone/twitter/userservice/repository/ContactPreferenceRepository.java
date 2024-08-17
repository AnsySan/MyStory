package com.clone.twitter.userservice.repository;

import com.clone.twitter.userservice.entity.ContactPreference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPreferenceRepository extends CrudRepository<ContactPreference, Long> {
}