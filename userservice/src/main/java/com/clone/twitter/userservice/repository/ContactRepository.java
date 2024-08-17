package com.clone.twitter.userservice.repository;

import com.clone.twitter.userservice.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

    Optional<Contact> findByContact(String title);
}