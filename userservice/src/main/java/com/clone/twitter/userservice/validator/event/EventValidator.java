package com.clone.twitter.userservice.validator.event;

import com.clone.twitter.userservice.dto.event.EventDto;

public interface EventValidator {
    void validate(EventDto eventDto);

    void validateOwnersRequiredSkills(EventDto event);

    void validateUserId(long userId);
}