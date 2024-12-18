package com.clone.twitter.userservice.service.goal.invitation;

import com.clone.twitter.userservice.dto.goal.GoalInvitationCreateDto;
import com.clone.twitter.userservice.dto.goal.GoalInvitationDto;
import com.clone.twitter.userservice.dto.goal.InvitationFilterDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.goal.GoalInvitationMapper;
import com.clone.twitter.userservice.model.RequestStatus;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.goal.GoalInvitation;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.goal.GoalInvitationRepository;
import com.clone.twitter.userservice.service.goal.GoalInvitationFilterService;
import com.clone.twitter.userservice.service.goal.GoalInvitationServiceImpl;
import com.clone.twitter.userservice.service.goal.GoalService;
import com.clone.twitter.userservice.service.user.UserService;
import com.clone.twitter.userservice.validator.goal.GoalInvitationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalInvitationServiceImplTest {

    @Mock
    private GoalInvitationRepository goalInvitationRepository;
    @Mock
    private GoalInvitationValidator goalInvitationValidator;
    @Spy
    private GoalInvitationMapper goalInvitationMapper = Mappers.getMapper(GoalInvitationMapper.class);
    @Mock
    private GoalInvitationFilterService goalInvitationFilterService;
    @Mock
    private GoalService goalService;
    @Mock
    private UserService userService;

    @InjectMocks
    private GoalInvitationServiceImpl goalInvitationServiceImpl;

    @Test
    void shouldReturnGoalInvitationById() {
        GoalInvitation goalInvitation = new GoalInvitation();
        when(goalInvitationRepository.findById(anyLong())).thenReturn(Optional.of(goalInvitation));

        assertEquals(goalInvitationServiceImpl.findById(1L), goalInvitation);

        verify(goalInvitationRepository, timeout(1)).findById(anyLong());
    }

    @Test
    void shouldThrowNotFoundException() {
        when(goalInvitationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> goalInvitationServiceImpl.findById(1L));

        verify(goalInvitationRepository, timeout(1)).findById(anyLong());
    }

    @Test
    void shouldSaveGoalInvitation() {
        GoalInvitationCreateDto goalInvitationCreateDto = new GoalInvitationCreateDto(1L, 2L, 1L, 1L);
        User inviter = new User();
        inviter.setUsername("inviter");
        User invited = new User();
        invited.setUsername("invited");
        Goal goal = new Goal();

        doNothing().when(goalInvitationValidator).validateUserIds(goalInvitationCreateDto);
        when(userService.findUserById(goalInvitationCreateDto.getInviterId())).thenReturn(inviter);
        when(userService.findUserById(goalInvitationCreateDto.getInvitedUserId())).thenReturn(invited);
        when(goalService.findGoalById(goalInvitationCreateDto.getGoalId())).thenReturn(goal);

        goalInvitationServiceImpl.createInvitation(goalInvitationCreateDto);

        verify(goalInvitationValidator, timeout(1)).validateUserIds(goalInvitationCreateDto);
        verify(userService, timeout(1)).findUserById(goalInvitationCreateDto.getInviterId());
        verify(userService, timeout(1)).findUserById(goalInvitationCreateDto.getInvitedUserId());
        verify(goalService, timeout(1)).findGoalById(goalInvitationCreateDto.getGoalId());
        verify(goalInvitationMapper, timeout(1)).toEntity(any(), any(), any(), any());
        verify(goalInvitationRepository, timeout(1)).save(any());
    }

    @Test
    void shouldThrowDataValidationExceptionBecauseInviterAndInvitedIdTheSame() {
        GoalInvitationCreateDto goalInvitationCreateDto = new GoalInvitationCreateDto(1L, 1L, 1L, 1L);

        doThrow(DataValidationException.class).when(goalInvitationValidator).validateUserIds(goalInvitationCreateDto);

        assertThrows(DataValidationException.class, () ->
                goalInvitationServiceImpl.createInvitation(goalInvitationCreateDto));

        verify(goalInvitationValidator, timeout(1)).validateUserIds(goalInvitationCreateDto);
    }

    @Test
    void shouldUpdateInvitationStatusToAccepted() {
        int currentGoals = 2;
        long goalInvitationId = 1;
        User invited = new User();
        invited.setId(10L);
        GoalInvitation goalInvitation = new GoalInvitation();
        goalInvitation.setStatus(RequestStatus.PENDING);
        goalInvitation.setInvited(invited);


        when(goalInvitationRepository.findById(goalInvitationId)).thenReturn(Optional.of(goalInvitation));
        doNothing().when(goalInvitationValidator).validateGoalAlreadyExistence(any(), any());
        doNothing().when(goalInvitationValidator).validateMaxGoals(currentGoals);
        when(goalService.findActiveGoalsByUserId(invited.getId())).thenReturn(currentGoals);
        when(goalInvitationRepository.save(goalInvitation)).thenReturn(goalInvitation);

        goalInvitationServiceImpl.acceptGoalInvitation(goalInvitationId);

        assertEquals(RequestStatus.ACCEPTED, goalInvitation.getStatus());

        verify(goalInvitationValidator, timeout(1)).validateGoalAlreadyExistence(any(), any());
        verify(goalService, timeout(1)).findActiveGoalsByUserId(invited.getId());
        verify(goalInvitationValidator, timeout(1)).validateMaxGoals(currentGoals);
        verify(goalInvitationRepository, timeout(1)).save(goalInvitation);
    }

    @Test
    void shouldThrowDataValidationExceptionBecauseUserHas3Goals() {
        int currentGoals = 3;
        long goalInvitationId = 1;
        User invited = new User();
        invited.setId(10L);
        GoalInvitation goalInvitation = new GoalInvitation();
        goalInvitation.setStatus(RequestStatus.PENDING);
        goalInvitation.setInvited(invited);


        when(goalInvitationRepository.findById(goalInvitationId)).thenReturn(Optional.of(goalInvitation));
        doNothing().when(goalInvitationValidator).validateGoalAlreadyExistence(any(), any());
        doThrow(DataValidationException.class).when(goalInvitationValidator).validateMaxGoals(currentGoals);
        when(goalService.findActiveGoalsByUserId(invited.getId())).thenReturn(currentGoals);

        assertThrows(DataValidationException.class, () ->
                goalInvitationServiceImpl.acceptGoalInvitation(goalInvitationId));

        verify(goalInvitationValidator, timeout(1)).validateGoalAlreadyExistence(any(), any());
        verify(goalService, timeout(1)).findActiveGoalsByUserId(invited.getId());
        verify(goalInvitationValidator, timeout(1)).validateMaxGoals(currentGoals);
    }

    @Test
    void shouldUpdateInvitationStatusToRejected() {
        long goalInvitationId = 1;
        User invited = new User();
        invited.setId(10L);
        GoalInvitation goalInvitation = new GoalInvitation();
        goalInvitation.setStatus(RequestStatus.PENDING);
        goalInvitation.setInvited(invited);


        when(goalInvitationRepository.findById(goalInvitationId)).thenReturn(Optional.of(goalInvitation));
        when(goalInvitationRepository.save(goalInvitation)).thenReturn(goalInvitation);


        goalInvitationServiceImpl.rejectGoalInvitation(goalInvitationId);

        assertEquals(RequestStatus.REJECTED, goalInvitation.getStatus());

        verify(goalInvitationRepository, timeout(1)).findById(goalInvitationId);
        verify(goalInvitationRepository, timeout(1)).save(goalInvitation);
    }

    @Test
    void getInvitations() {
        Stream<GoalInvitation> invitationsStream = getInvitationList().stream();
        InvitationFilterDto filterDto = new InvitationFilterDto();

        when(goalInvitationFilterService.applyFilters(any(), any())).thenReturn(invitationsStream);
        when(goalInvitationRepository.findAll()).thenReturn(getInvitationList());

        List<GoalInvitationDto> result = goalInvitationServiceImpl.getInvitations(filterDto);

        assertEquals(result, getInvitationList().stream().map(goalInvitationMapper::toDto).toList());

        verify(goalInvitationRepository, timeout(1)).findAll();
        verify(goalInvitationFilterService, timeout(1)).applyFilters(any(), any());
    }

    private List<GoalInvitation> getInvitationList() {
        User inviter1 = User.builder().id(1).username("inviter1").email("test@gmail.com").build();
        User inviter2 = User.builder().id(2).username("inviter2").email("test@gmail.com").build();
        User inviter3 = User.builder().id(3).username("inviter3").email("test@gmail.com").build();

        User invited1 = User.builder().id(1).username("invited1").email("test@gmail.com").build();
        User invited2 = User.builder().id(2).username("invited2").email("test@gmail.com").build();
        User invited3 = User.builder().id(3).username("invited3").email("test@gmail.com").build();

        Goal goal1 = Goal.builder().id(1L).title("goal1").build();
        Goal goal2 = Goal.builder().id(2L).title("goal2").build();
        Goal goal3 = Goal.builder().id(3L).title("goal3").build();

        return List.of(
                new GoalInvitation(1, goal1, inviter1, invited1, RequestStatus.PENDING, LocalDateTime.now(), LocalDateTime.now()),
                new GoalInvitation(2, goal2, inviter2, invited2, RequestStatus.PENDING, LocalDateTime.now(), LocalDateTime.now()),
                new GoalInvitation(3, goal3, inviter3, invited3, RequestStatus.PENDING, LocalDateTime.now(), LocalDateTime.now())
        );
    }
}
