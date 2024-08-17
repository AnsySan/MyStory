package com.clone.twitter.userservice.controller;

import com.clone.twitter.userservice.dto.ContactDto;
import com.clone.twitter.userservice.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/contact/")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ContactDto getContact(@RequestParam String contact) {
        return contactService.getContact(contact);
    }
}