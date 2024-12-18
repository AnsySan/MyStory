package com.clone.twitter.userservice.controller.mentorship;

import com.clone.twitter.userservice.dto.mentorship.MentorshipRequestDto;
import com.clone.twitter.userservice.dto.mentorship.RejectionDto;
import com.clone.twitter.userservice.dto.mentorship.RequestFilterDto;
import com.clone.twitter.userservice.service.mentorship.MentorshipRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/mentorship/request")
@RequiredArgsConstructor
@Validated
public class MentorshipRequestController {
    private final MentorshipRequestService mentorshipRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MentorshipRequestDto requestMentorship(@Valid @RequestBody MentorshipRequestDto dto) {
        return mentorshipRequestService.requestMentorship(dto);
    }

    @GetMapping
    public List<MentorshipRequestDto> getRequests(@Valid @RequestBody RequestFilterDto filter) {
        return mentorshipRequestService.getRequests(filter);
    }

    @PutMapping(path = "/accept/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MentorshipRequestDto acceptRequest(@Positive @PathVariable(name = "id") Long id) {
        return mentorshipRequestService.acceptRequest(id);
    }

    @PutMapping(path = "/reject/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MentorshipRequestDto rejectRequest(@Positive @PathVariable(name = "id") Long id,
                                              @Valid @RequestBody RejectionDto rejection) {
        return mentorshipRequestService.rejectRequest(id, rejection);
    }
}