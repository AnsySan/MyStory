package com.clone.twitter.userservice.service.goal;

import com.clone.twitter.userservice.dto.goal.GoalInvitationCreateDto;
import com.clone.twitter.userservice.dto.goal.GoalInvitationDto;
import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.goal.GoalInvitationMapper;
import com.clone.twitter.userservice.model.RequestStatus;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.goal.GoalInvitation;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.goal.GoalInvitationRepository;
import com.clone.twitter.userservice.service.user.UserService;
import com.clone.twitter.userservice.validator.goal.GoalInvitationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GoalInvitationServiceImpl implements GoalInvitationService {

    private final GoalInvitationRepository goalInvitationRepository;
    private final GoalInvitationValidator goalInvitationValidator;
    private final GoalInvitationMapper goalInvitationMapper;
    private final GoalInvitationFilterService goalInvitationFilterService;
    private final GoalService goalService;
    private final UserService userService;

    @Override
    @Transactional
    public GoalInvitation findById(long id) {
        return goalInvitationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("GoalInvitation with id %s not found", id)));
    }

    @Override
    @Transactional
    public void createInvitation(GoalInvitationCreateDto invitation) {
        goalInvitationValidator.validateUserIds(invitation);

        User inviter = userService.findUserById(invitation.getInviterId());
        User invitedUser = userService.findUserById(invitation.getInvitedUserId());
        Goal goal = goalService.findGoalById(invitation.getGoalId());
        GoalInvitation goalInvitationEntity = goalInvitationMapper.toEntity(inviter, invitedUser, goal, RequestStatus.PENDING);

        goalInvitationRepository.save(goalInvitationEntity);
    }

    @Override
    @Transactional
    public void acceptGoalInvitation(long id) {
        GoalInvitation goalInvitation = findById(id);
        User invited = goalInvitation.getInvited();

        goalInvitationValidator.validateGoalAlreadyExistence(goalInvitation.getGoal(), invited);
        goalInvitationValidator.validateMaxGoals(goalService.findActiveGoalsByUserId(invited.getId()));

        goalInvitation.setStatus(RequestStatus.ACCEPTED);
        goalInvitationRepository.save(goalInvitation);
    }

    @Override
    public void rejectGoalInvitation(long id) {
        GoalInvitation goalInvitation = findById(id);

        goalInvitation.setStatus(RequestStatus.REJECTED);
        goalInvitationRepository.save(goalInvitation);
    }

    @Override
    public List<GoalInvitationDto> getInvitations(InvitationFilterDto filterDto) {
        Stream<GoalInvitation> invitationStream = goalInvitationRepository.findAll().stream();
        return goalInvitationFilterService.applyFilters(invitationStream, filterDto)
                .map(goalInvitationMapper::toDto)
                .toList();
    }
}