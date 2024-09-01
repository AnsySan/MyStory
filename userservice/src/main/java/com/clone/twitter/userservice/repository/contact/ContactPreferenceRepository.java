package com.clone.twitter.userservice.repository.contact;

import com.clone.twitter.userservice.model.contact.ContactPreference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPreferenceRepository extends CrudRepository<ContactPreference, Long> {

    Optional<ContactPreference> findByUserUsername(String username);

}