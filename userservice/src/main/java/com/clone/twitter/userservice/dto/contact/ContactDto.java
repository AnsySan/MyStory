package com.clone.twitter.userservice.dto.contact;

import com.clone.twitter.userservice.model.contact.ContactType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {

    private long id;
    @NotNull
    private Long userId;
    @NotNull
    private String contact;
    @NotNull
    private ContactType type;

}