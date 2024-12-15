package com.clone.twitter.userservice.mapper.goal;

import com.clone.twitter.userservice.dto.goal.GoalInvitationDto;
import com.clone.twitter.userservice.model.RequestStatus;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.goal.GoalInvitation;
import com.clone.twitter.userservice.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoalInvitationMapper {

    @Mapping(source = "inviter", target = "inviter")
    @Mapping(source = "invited", target = "invited")
    @Mapping(source = "goal", target = "goal")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    GoalInvitation toEntity(User inviter, User invited, Goal goal, RequestStatus status);

    GoalInvitationDto toDto(GoalInvitation goalInvitation);
}