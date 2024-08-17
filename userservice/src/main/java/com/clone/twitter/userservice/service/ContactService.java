package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.dto.ContactDto;
import com.clone.twitter.userservice.entity.Contact;
import com.clone.twitter.userservice.mapper.ContactMapper;
import com.clone.twitter.userservice.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;

    public ContactDto getContact(String title) {
        Contact contact = contactRepository.findByContact(title)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));
        return contactMapper.toDto(contact);
    }

}