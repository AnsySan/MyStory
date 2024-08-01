package com.clone.instagram.authSetvice.Payload;

import com.clone.instagram.authSetvice.messaging.UserEventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEventPayload {

    private String id;
    private String username;
    private String email;
    private String displayName;
    private String profilePictureUrl;
    private String oldProfilePicUrl;
    private UserEventType eventType;
}