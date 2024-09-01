package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.contact.ContactPreferenceDto;
import com.clone.twitter.userservice.service.contact.ContactPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/contacts/preferences")
public class ContactPreferenceController {

    private final ContactPreferenceService contactService;

    @GetMapping("/{userName}")
    public ContactPreferenceDto getContact(@RequestParam String contact) {
        return contactService.getContact(contact);
    }
}