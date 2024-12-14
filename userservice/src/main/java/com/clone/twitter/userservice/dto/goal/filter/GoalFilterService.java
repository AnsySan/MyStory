package com.clone.twitter.userservice.dto.goal.filter;


import com.clone.twitter.userservice.dto.goal.GoalFilterDto;
import com.clone.twitter.userservice.model.goal.Goal;

import java.util.stream.Stream;

public interface GoalFilterService {

    Stream<Goal> applyFilters(Stream<Goal> goals, GoalFilterDto goalFilterDto);
}