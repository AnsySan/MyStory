package com.clone.twitter.userservice.dto.user;

import com.clone.twitter.userservice.model.user.UserProfilePicture;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.clone.twitter.userservice.model.contact.PreferredContact;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "E-mail can't be empty")
    @Email
    private String email;

    @NotBlank(message = "Phone can't be empty")
    @Pattern(regexp = "^\\+\\d{11}$")
    private String phone;

    @NotBlank(message = "Password can't be empty")
    private String password;
    private boolean active;

    @NotNull(message = "Country ID can't be empty")
    private long countryId;

    private PreferredContact preferredContact;

    private UserProfilePicture userProfilePicture;
}