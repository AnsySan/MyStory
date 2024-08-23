package com.clone.twitter.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private UserDto user;
    private String message;
}