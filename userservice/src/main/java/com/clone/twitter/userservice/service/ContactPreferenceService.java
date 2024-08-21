package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.dto.ContactPreferenceDto;
import com.clone.twitter.userservice.entity.ContactPreference;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.ContactPreferenceMapper;
import com.clone.twitter.userservice.repository.ContactPreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactPreferenceService {

    private final ContactPreferenceRepository contactRepository;
    private final ContactPreferenceMapper contactPreferenceMapper;

    @Transactional(readOnly = true)
    public ContactPreferenceDto getContact(String username) {
        ContactPreference contactPreference = contactRepository.findByUserUsername(username)
                .orElseThrow(() -> new NotFoundException("Contact with username " + username + " not found"));

        return contactPreferenceMapper.toDto(contactPreference);
    }
}