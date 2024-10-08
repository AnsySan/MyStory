package com.clone.twitter.notificationservice.client;

import com.clone.twitter.notificationservice.dto.ContactDto;
import com.clone.twitter.notificationservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${user-service.host}:${user-service.port}")
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable long id);

    @GetMapping("/api/v1/users/contact/")
    ContactDto getContact(@RequestParam String contact);
}