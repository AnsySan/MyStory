package com.clone.twitter.userservice.dto.user;

import com.clone.twitter.userservice.model.user.UserProfilePicture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.clone.twitter.userservice.model.contact.PreferredContact;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    @NotBlank
    @Size(max = 64, message = "username should be less than 65 symbols")
    private String username;

    @NotBlank
    @Size(max = 64, message = "email should be less than 65 symbols")
    private String email;

    @NotBlank
    @Size(max = 32, message = "phone should be less than 32 symbols")
    private String phone;

    @NotBlank
    @Size(max = 128, message = "password should be less than 129 symbols")
    private String password;

    private Long countryId;

    private boolean active;

    private UserProfilePicture userProfilePicture;

    private PreferredContact preferredContact;
}