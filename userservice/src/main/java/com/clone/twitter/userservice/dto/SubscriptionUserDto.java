package com.clone.twitter.userservice.dto;

import lombok.Data;

@Data
public class SubscriptionUserDto {
    private Long id;
    private String username;
    private String email;
}