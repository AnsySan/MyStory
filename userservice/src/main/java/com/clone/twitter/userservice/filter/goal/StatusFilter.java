package com.clone.twitter.userservice.filter.goal;

import com.clone.twitter.userservice.dto.goal.GoalFilterDto;
import com.clone.twitter.userservice.model.goal.Goal;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class StatusFilter implements GoalFilter {

    @Override
    public boolean isAcceptable(GoalFilterDto goalFilterDto) {
        return goalFilterDto.getGoalStatus() != null;
    }

    @Override
    public Stream<Goal> applyFilter(Stream<Goal> goals, GoalFilterDto goalFilterDto) {
        return goals.filter(goal -> goal.getStatus().equals(goalFilterDto.getGoalStatus()));
    }
}