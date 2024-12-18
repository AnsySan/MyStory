package com.clone.twitter.userservice.service.goal;


import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.model.goal.GoalInvitation;

import java.util.stream.Stream;

public interface GoalInvitationFilterService {

    Stream<GoalInvitation> applyFilters(Stream<GoalInvitation> goalInvitations, InvitationFilterDto filterDto);
}