package com.clone.twitter.userservice.dto.subscription;

import lombok.Data;

@Data
public class SubscriptionUserDto {
    private Long id;
    private String username;
    private String email;
}