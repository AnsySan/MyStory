package com.clone.twitter.user_service.repository.contact;

import com.clone.twitter.user_service.model.contact.ContactPreference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPreferenceRepository extends CrudRepository<ContactPreference, Long> {

    Optional<ContactPreference> findByUserUsername(String username);

}