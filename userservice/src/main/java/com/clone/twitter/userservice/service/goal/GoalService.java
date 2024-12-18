package com.clone.twitter.userservice.service.goal;


import com.clone.twitter.userservice.dto.goal.GoalDto;
import com.clone.twitter.userservice.dto.goal.GoalFilterDto;
import com.clone.twitter.userservice.model.goal.Goal;

import java.util.List;

public interface GoalService {
    List<GoalDto> getGoalsByUser(long userId, GoalFilterDto goalFilterDto);

    GoalDto createGoal(Long userId, GoalDto goal);

    GoalDto updateGoal(long userId, Long goalId, GoalDto goalDto);

    GoalDto deleteGoal(long goalId);

    List<GoalDto> getSubtasksByGoalId(long goalId, GoalFilterDto filter);

    Goal findGoalById(long id);

    int findActiveGoalsByUserId(long id);

    void delete(Goal goal);
}