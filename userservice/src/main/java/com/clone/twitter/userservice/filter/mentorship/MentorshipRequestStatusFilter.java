package com.clone.twitter.userservice.filter.mentorship;

import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;
import com.clone.twitter.userservice.model.MentorshipRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MentorshipRequestStatusFilter implements MentorshipRequestFilter {
    @Override
    public boolean isApplicable(RequestFilterDto filter) {
        return filter.getStatusPattern() != null;
    }

    @Override
    public Stream<MentorshipRequest> apply(Stream<MentorshipRequest> entities, RequestFilterDto filterDto) {
        var statusFilter = filterDto.getStatusPattern();

        return entities.filter(entity -> entity.getStatus().equals(statusFilter));
    }
}