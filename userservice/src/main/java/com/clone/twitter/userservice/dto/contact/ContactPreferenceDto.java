package com.clone.twitter.userservice.dto.contact;

import com.clone.twitter.userservice.model.contact.PreferredContact;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ContactPreferenceDto {

    private Long id;

    @NotNull(message = "UserId should not be null")
    @Positive(message = "UserId should be positive")
    private Long userId;

    @NotNull(message = "Preference should not be null")
    private PreferredContact preference;
}