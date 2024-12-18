package com.clone.twitter.userservice.controller.goal;

import com.clone.twitter.userservice.dto.goal.GoalInvitationCreateDto;
import com.clone.twitter.userservice.dto.goal.GoalInvitationDto;
import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.service.goal.GoalInvitationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("goals/invitations")
@Validated
public class GoalInvitationController {

    private final GoalInvitationService goalInvitationService;

    @PostMapping
    public void createInvitation(@Valid GoalInvitationCreateDto invitation) {
        goalInvitationService.createInvitation(invitation);
    }

    @PatchMapping("accept")
    public void acceptGoalInvitation(@Positive @RequestParam long id) {
        goalInvitationService.acceptGoalInvitation(id);
    }

    @PatchMapping("reject")
    public void rejectGoalInvitation(@Positive @RequestParam long id) {
        goalInvitationService.rejectGoalInvitation(id);
    }

    @PostMapping("get")
    public List<GoalInvitationDto> getInvitations(@Valid InvitationFilterDto filter) {
        return goalInvitationService.getInvitations(filter);
    }
}