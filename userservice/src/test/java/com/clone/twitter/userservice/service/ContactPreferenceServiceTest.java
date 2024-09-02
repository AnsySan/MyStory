package com.clone.twitter.userservice.service;

import com.clone.twitter.userservice.dto.contact.ContactPreferenceDto;
import com.clone.twitter.userservice.model.contact.ContactPreference;
import com.clone.twitter.userservice.model.contact.PreferredContact;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.contact.ContactPreferenceMapper;
import com.clone.twitter.userservice.repository.contact.ContactPreferenceRepository;
import com.clone.twitter.userservice.service.contact.ContactPreferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactPreferenceServiceTest {

    @Mock
    private ContactPreferenceRepository contactRepository;
    @Mock
    private ContactPreferenceMapper contactPreferenceMapper;

    @InjectMocks
    private ContactPreferenceService contactPreferenceService;

    private final String username = "username";
    private ContactPreferenceDto contactPreferenceDto;
    private ContactPreference contactPreference;

    @BeforeEach
    void setUp() {
        contactPreference = ContactPreference.builder()
                .id(1L)
                .preference(PreferredContact.TELEGRAM)
                .user(User.builder().id(2L).build())
                .build();

        contactPreferenceDto = ContactPreferenceDto.builder()
                .id(1L)
                .preference(PreferredContact.TELEGRAM)
                .userId(2L)
                .build();
    }

    @Test
    void getContact() {
        when(contactRepository.findByUserUsername(username)).thenReturn(Optional.of(contactPreference));
        when(contactPreferenceMapper.toDto(contactPreference)).thenReturn(contactPreferenceDto);

        ContactPreferenceDto actual = contactPreferenceService.getContact(username);
        assertEquals(contactPreferenceDto, actual);

        InOrder inOrder = inOrder(contactPreferenceMapper, contactRepository);
        inOrder.verify(contactRepository).findByUserUsername(username);
        inOrder.verify(contactPreferenceMapper).toDto(contactPreference);
    }

    @Test
    void getContactNotFoundException() {
        when(contactRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> contactPreferenceService.getContact(username));
        assertEquals("Contact with username " + username + " not found", e.getMessage());
    }
}