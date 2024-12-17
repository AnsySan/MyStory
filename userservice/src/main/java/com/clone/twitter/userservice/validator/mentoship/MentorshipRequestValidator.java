package com.clone.twitter.userservice.validator.mentoship;


import com.clone.twitter.userservice.dto.mentorship.MentorshipRequestDto;
import com.clone.twitter.userservice.model.MentorshipRequest;

public interface MentorshipRequestValidator {
    void validateMentorshipRequest(MentorshipRequestDto dto);

    MentorshipRequest validateMentorshipRequestExistence(long id);

    void validateMentor(MentorshipRequest entity);
}