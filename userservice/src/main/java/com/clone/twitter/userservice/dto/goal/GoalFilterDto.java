package com.clone.twitter.userservice.dto.goal;

import com.clone.twitter.userservice.model.goal.GoalStatus;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.validator.enumvalidator.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalFilterDto {
    private User mentor;

    @Length(max = 64, message = "Title cannot be longer than 64 characters")
    private String title;

    @EnumValidator(enumClass = GoalStatus.class, message = "Invalid Goal Status")
    private GoalStatus goalStatus;
}