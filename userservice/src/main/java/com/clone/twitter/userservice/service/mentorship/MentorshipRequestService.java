package com.clone.twitter.userservice.service.mentorship;

import com.clone.twitter.userservice.dto.mentorship.MentorshipRequestDto;
import com.clone.twitter.userservice.dto.mentorship.RejectionDto;
import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;

import java.util.List;

public interface MentorshipRequestService {
    MentorshipRequestDto requestMentorship(MentorshipRequestDto dto);

    List<MentorshipRequestDto> getRequests(RequestFilterDto requestFilterDto);

    MentorshipRequestDto acceptRequest(Long id);

    MentorshipRequestDto rejectRequest(Long id, RejectionDto rejection);
}