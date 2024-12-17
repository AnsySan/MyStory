package com.clone.twitter.userservice.validator.goal;


import com.clone.twitter.userservice.dto.goal.GoalInvitationCreateDto;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.user.User;

public interface GoalInvitationValidator {

    void validateUserIds(GoalInvitationCreateDto goalInvitationDto);

    void validateMaxGoals(int currentGoals);

    void validateGoalAlreadyExistence(Goal goal, User user);
}