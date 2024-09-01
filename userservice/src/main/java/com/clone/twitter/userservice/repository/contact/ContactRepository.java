package com.clone.twitter.userservice.repository.contact;

import com.clone.twitter.userservice.model.contact.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}