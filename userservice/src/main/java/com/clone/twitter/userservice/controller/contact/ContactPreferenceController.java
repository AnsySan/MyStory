package com.clone.twitter.userservice.controller.contact;

import com.clone.twitter.userservice.dto.contact.ContactPreferenceDto;
import com.clone.twitter.userservice.service.contact.ContactPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Contacts")
@RestController
@RequestMapping("/contacts/preferences")
@RequiredArgsConstructor
public class ContactPreferenceController {

    private final ContactPreferenceService contactPreferenceService;

    @GetMapping("/{userName}")
    @Operation(summary = "Get contact preference by user username")
    public ContactPreferenceDto getContactPreference(@PathVariable String userName) {
        return contactPreferenceService.getContact(userName);
    }
}