package com.clone.instagram.authSetvice.Payload.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String profilePicture;
}
