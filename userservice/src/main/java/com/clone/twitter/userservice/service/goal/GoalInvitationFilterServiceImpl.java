package com.clone.twitter.userservice.service.goal;

import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.filter.goal.GoalInvitationFilter;
import com.clone.twitter.userservice.model.goal.GoalInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GoalInvitationFilterServiceImpl implements GoalInvitationFilterService {

    private final List<GoalInvitationFilter> filters;

    @Override
    public Stream<GoalInvitation> applyFilters(Stream<GoalInvitation> goalInvitations, InvitationFilterDto filterDto) {
        if (filterDto != null) {
            for (GoalInvitationFilter filter : filters) {
                if (filter.isAcceptable(filterDto)) {
                    goalInvitations = filter.applyFilter(goalInvitations, filterDto);
                }
            }
        }

        return goalInvitations;
    }
}