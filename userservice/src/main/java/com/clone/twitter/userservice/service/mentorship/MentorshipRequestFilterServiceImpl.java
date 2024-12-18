package com.clone.twitter.userservice.service.mentorship;

import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;
import com.clone.twitter.userservice.filter.mentorship.MentorshipRequestFilter;
import com.clone.twitter.userservice.model.MentorshipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MentorshipRequestFilterServiceImpl implements MentorshipRequestFilterService {
    private final List<MentorshipRequestFilter> filters;

    @Override
    public Stream<MentorshipRequest> apply(Stream<MentorshipRequest> entities, RequestFilterDto internshipFilterDto) {
        for (MentorshipRequestFilter filter : filters) {
            if (filter.isApplicable(internshipFilterDto)) {
                entities = filter.apply(entities, internshipFilterDto);
            }
        }

        return entities;
    }
}