package com.clone.twitter.userservice.filter.goal;

import com.clone.twitter.userservice.dto.goal.GoalFilterDto;
import com.clone.twitter.userservice.model.goal.Goal;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TitleFilter implements GoalFilter {

    @Override
    public boolean isAcceptable(GoalFilterDto goalFilterDto) {
        return goalFilterDto.getTitle() != null;
    }

    @Override
    public Stream<Goal> applyFilter(Stream<Goal> goals, GoalFilterDto goalFilterDto) {
        return goals.filter(goal -> goal.getTitle().equals(goalFilterDto.getTitle()));
    }
}