package com.clone.twitter.userservice.service.goal;

import com.clone.twitter.userservice.dto.goal.GoalInvitationCreateDto;
import com.clone.twitter.userservice.dto.goal.GoalInvitationDto;
import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.model.goal.GoalInvitation;

import java.util.List;

public interface GoalInvitationService {

    GoalInvitation findById(long id);

    void createInvitation(GoalInvitationCreateDto invitation);

    void acceptGoalInvitation(long id);

    void rejectGoalInvitation(long id);

    List<GoalInvitationDto> getInvitations(InvitationFilterDto filterDto);
}