package com.clone.twitter.postservice.dto.user;

import com.clone.twitter.postservice.entity.PreferredContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private UserProfilePic userProfilePic;
    private PreferredContact preferredContact;
}