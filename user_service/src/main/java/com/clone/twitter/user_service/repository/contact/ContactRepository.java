package com.clone.twitter.user_service.repository.contact;

import com.clone.twitter.user_service.model.contact.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}