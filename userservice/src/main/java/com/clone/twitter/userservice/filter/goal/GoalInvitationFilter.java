package com.clone.twitter.userservice.filter.goal;


import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.model.goal.GoalInvitation;

import java.util.stream.Stream;

public interface GoalInvitationFilter {

    boolean isAcceptable(InvitationFilterDto filterDto);

    Stream<GoalInvitation> applyFilter(Stream<GoalInvitation> invitations, InvitationFilterDto filterDto);
}