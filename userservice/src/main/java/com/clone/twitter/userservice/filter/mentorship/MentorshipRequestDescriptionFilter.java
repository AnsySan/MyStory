package com.clone.twitter.userservice.filter.mentorship;

import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;
import com.clone.twitter.userservice.model.MentorshipRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MentorshipRequestDescriptionFilter implements MentorshipRequestFilter {
    @Override
    public boolean isApplicable(RequestFilterDto filter) {
        return filter.getDescriptionPattern() != null;
    }

    @Override
    public Stream<MentorshipRequest> apply(Stream<MentorshipRequest> entities, RequestFilterDto filterDto) {
        var descriptionFilter = filterDto.getDescriptionPattern();

        return entities.filter(entity -> entity.getDescription().contains(descriptionFilter));
    }
}