package com.clone.twitter.userservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ProfileViewEventDto{
    @NotNull
    private Long observerId;
    @NotNull
    private Long observedId;
    @NotNull
    private LocalDateTime viewedAt;
}