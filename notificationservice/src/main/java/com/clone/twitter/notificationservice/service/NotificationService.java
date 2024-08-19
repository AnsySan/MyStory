package com.clone.twitter.notificationservice.service;

import com.clone.twitter.notificationservice.dto.UserDto;
import com.clone.twitter.notificationservice.enums.PreferredContact;

public interface NotificationService {

    void send(UserDto user, String message);

    PreferredContact getPreferredContact();
}