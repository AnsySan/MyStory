package com.clone.twitter.userservice.dto.goal;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalInvitationDto {
    private long id;
    private UserDto inviter;
    private UserDto invited;
    private GoalDto goal;
    private RequestStatus status;
}
