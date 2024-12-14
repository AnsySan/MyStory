package com.clone.twitter.userservice.filter.goal;


import com.clone.twitter.userservice.dto.goal.GoalFilterDto;
import com.clone.twitter.userservice.model.goal.Goal;

import java.util.stream.Stream;

public interface GoalFilter {

    public boolean isAcceptable(GoalFilterDto goalFilterDto);

    public Stream<Goal> applyFilter(Stream<Goal> goals, GoalFilterDto goalFilterDto);

}