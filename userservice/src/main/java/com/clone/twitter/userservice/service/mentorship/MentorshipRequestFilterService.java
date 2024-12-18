package com.clone.twitter.userservice.service.mentorship;

import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;
import com.clone.twitter.userservice.model.MentorshipRequest;

import java.util.stream.Stream;

public interface MentorshipRequestFilterService {
    public Stream<MentorshipRequest> apply(Stream<MentorshipRequest> entities, RequestFilterDto internshipFilterDto);
}