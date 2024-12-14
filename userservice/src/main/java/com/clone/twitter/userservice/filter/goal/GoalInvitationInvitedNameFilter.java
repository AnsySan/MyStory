package com.clone.twitter.userservice.filter.goal;

import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.model.goal.GoalInvitation;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class GoalInvitationInvitedNameFilter implements GoalInvitationFilter {

    @Override
    public boolean isAcceptable(InvitationFilterDto filterDto) {
        return filterDto.getInvitedNamePattern() != null;
    }

    @Override
    public Stream<GoalInvitation> applyFilter(Stream<GoalInvitation> invitations, InvitationFilterDto filterDto) {
        return invitations.filter(invitation -> invitation.getInvited().getUsername().startsWith(filterDto.getInvitedNamePattern()));
    }
}