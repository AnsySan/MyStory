package com.clone.twitter.userservice.controller.mentorship;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.service.mentorship.MentorshipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class MentorshipController {
    private final MentorshipService mentorshipService;

    public List<UserDto> getMentees(@Positive long userId) {
        return mentorshipService.getMentees(userId);
    }

    public List<UserDto> getMentors(@Positive long userId) {
        return mentorshipService.getMentors(userId);
    }

    public void deleteMentee(@Positive long menteeId, @Positive long mentorId) {
        mentorshipService.deleteMentee(menteeId, mentorId);
    }

    public void deleteMentor(@Positive long menteeId, @Positive long mentorId) {
        mentorshipService.deleteMentor(menteeId, mentorId);
    }
}