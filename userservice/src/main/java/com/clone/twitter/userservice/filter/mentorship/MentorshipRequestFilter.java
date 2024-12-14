package com.clone.twitter.userservice.filter.mentorship;

import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;
import com.clone.twitter.userservice.model.MentorshipRequest;

import java.util.stream.Stream;

public interface MentorshipRequestFilter {
    boolean isApplicable(RequestFilterDto filter);

    Stream<MentorshipRequest> apply(Stream<MentorshipRequest> entities, RequestFilterDto filterDto);
}