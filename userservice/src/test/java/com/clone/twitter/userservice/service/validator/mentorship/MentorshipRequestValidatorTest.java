package com.clone.twitter.userservice.service.validator.mentorship;

import com.clone.twitter.userservice.dto.mentorship.MentorshipRequestDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.model.MentorshipRequest;
import com.clone.twitter.userservice.model.RequestStatus;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.mentorship.MentorshipRequestRepository;
import com.clone.twitter.userservice.validator.mentoship.MentorshipRequestValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MentorshipRequestValidatorTest {
    private MentorshipRequestDto notValidDto;
    private MentorshipRequestDto validDto;

    private MentorshipRequest validMentorshipRequest;

    @Mock
    private MentorshipRequestRepository mentorshipRequestRepository;
    @InjectMocks
    private MentorshipRequestValidatorImpl mentorshipRequestValidator;

    @Captor
    private ArgumentCaptor<Long> requesterIdCaptor;
    @Captor
    private ArgumentCaptor<Long> receiverIdCaptor;


    @BeforeEach
    public void init() {
        long requesterId4 = 4L;
        long receiverId4 = 4L;
        long requesterId = 3L;
        long receiverId = 5L;
        LocalDateTime createdAtOfInitialMentorshipRequest = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime updatedAtOfInitialMentorshipRequest = LocalDateTime.of(2024, 1, 1, 15, 0);
        User requesterOfInitialMentorshipRequest = User.builder()
                .id(requesterId)
                .username("NameOfUserId3")
                .build();
        User receiverOfInitialMentorshipRequest = User.builder()
                .id(receiverId)
                .username("NameOfUserId4")
                .build();
        notValidDto = MentorshipRequestDto.builder()
                .id(1L)
                .description("description of notValidDto")
                .requesterId(requesterId4)
                .receiverId(receiverId4)
                .status(RequestStatus.PENDING)
                .rejectionReason("rejection reason of notValidDto")
                .createdAt(createdAtOfInitialMentorshipRequest)
                .updatedAt(updatedAtOfInitialMentorshipRequest)
                .build();

        validDto = MentorshipRequestDto.builder()
                .id(1L)
                .description("description of validDto")
                .requesterId(requesterId)
                .receiverId(receiverId)
                .status(RequestStatus.PENDING)
                .rejectionReason("rejection reason of validDto")
                .createdAt(createdAtOfInitialMentorshipRequest)
                .updatedAt(updatedAtOfInitialMentorshipRequest)
                .build();

        validMentorshipRequest = MentorshipRequest.builder()
                .id(1L)
                .description("description of validDto")
                .requester(requesterOfInitialMentorshipRequest)
                .receiver(receiverOfInitialMentorshipRequest)
                .status(RequestStatus.PENDING)
                .rejectionReason("rejection reason of validDto")
                .createdAt(createdAtOfInitialMentorshipRequest)
                .updatedAt(updatedAtOfInitialMentorshipRequest)
                .build();
    }

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(mentorshipRequestValidator, "MONTHS", 3L);
    }

    @Test
    public void testValidateMentorshipRequestThrowsExceptionWhenRequesterIdAndReceiverIdSame() {
        String expected = "a request id and a receiver id can not be same";

        DataValidationException actual = assertThrows(DataValidationException.class,
                () -> mentorshipRequestValidator.validateMentorshipRequest(notValidDto));

        assertEquals(expected, actual.getMessage());
        verifyNoInteractions(mentorshipRequestRepository);
    }

    @Test
    public void testValidateMentorshipRequestCallsMentorshipRequestRepositoryWithSertanParameters() {

        mentorshipRequestValidator.validateMentorshipRequest(validDto);
        verify(mentorshipRequestRepository, times(1))
                .findLatestRequest(requesterIdCaptor.capture(), receiverIdCaptor.capture());

        Long actualRequesterId = requesterIdCaptor.getValue();
        Long actualReceiverId = receiverIdCaptor.getValue();

        assertEquals(validDto.getRequesterId(), actualRequesterId);
        assertEquals(validDto.getReceiverId(), actualReceiverId);
    }

    @Test
    public void testValidateMentorshipRequestWhenLastRequestWasOneSecondBeforeThreeMonths() {
        LocalDateTime oneSecondBeforeThreeMonthsAgo = LocalDateTime.now().minusMonths(3).minusSeconds(1);
        validDto.setCreatedAt(oneSecondBeforeThreeMonthsAgo);
        validMentorshipRequest.setCreatedAt(oneSecondBeforeThreeMonthsAgo);

        when(mentorshipRequestRepository.findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId()))
                .thenReturn(Optional.of(validMentorshipRequest));
        mentorshipRequestValidator.validateMentorshipRequest(validDto);

        verify(mentorshipRequestRepository, times(1))
                .findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId());
        verifyNoMoreInteractions(mentorshipRequestRepository);
    }

    @Test
    public void testValidateMentorshipRequestWhenLastRequestWasOneMonthBeforeThreeMonths() {
        LocalDateTime oneSecondBeforeThreeMonthsAgo = LocalDateTime.now().minusMonths(4);
        validDto.setCreatedAt(oneSecondBeforeThreeMonthsAgo);
        validMentorshipRequest.setCreatedAt(oneSecondBeforeThreeMonthsAgo);

        when(mentorshipRequestRepository.findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId()))
                .thenReturn(Optional.of(validMentorshipRequest));
        mentorshipRequestValidator.validateMentorshipRequest(validDto);

        verify(mentorshipRequestRepository, times(1))
                .findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId());
        verifyNoMoreInteractions(mentorshipRequestRepository);
    }

    @Test
    public void testValidateMentorshipRequestWhenLastRequestWasOneSecondAfterThreeMonthsThrowsException() {
        LocalDateTime oneSecondBeforeThreeMonthsAgo = LocalDateTime.now().minusMonths(3).plusHours(1);
        validDto.setCreatedAt(oneSecondBeforeThreeMonthsAgo);
        validMentorshipRequest.setCreatedAt(oneSecondBeforeThreeMonthsAgo);

        when(mentorshipRequestRepository.findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId()))
                .thenReturn(Optional.of(validMentorshipRequest));

        DataValidationException actualException = assertThrows(DataValidationException.class,
                () -> mentorshipRequestValidator.validateMentorshipRequest(validDto));
        String expectedMessage = String.format("a mentorship request to user with id %d" +
                " already has been made in last three months", validDto.getReceiverId());
        assertEquals(expectedMessage, actualException.getMessage());

        verify(mentorshipRequestRepository, times(1))
                .findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId());
        verifyNoMoreInteractions(mentorshipRequestRepository);
    }

    @Test
    public void testValidateMentorshipRequestWhenLastRequestWasOneMonthAfterThreeMonthsThrowsException() {
        LocalDateTime oneSecondBeforeThreeMonthsAgo = LocalDateTime.now().minusMonths(3).plusMonths(1);
        validDto.setCreatedAt(oneSecondBeforeThreeMonthsAgo);
        validMentorshipRequest.setCreatedAt(oneSecondBeforeThreeMonthsAgo);

        when(mentorshipRequestRepository.findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId()))
                .thenReturn(Optional.of(validMentorshipRequest));

        DataValidationException actualException = assertThrows(DataValidationException.class,
                () -> mentorshipRequestValidator.validateMentorshipRequest(validDto));
        String expectedMessage = String.format("a mentorship request to user with id %d" +
                " already has been made in last three months", validDto.getReceiverId());

        assertEquals(expectedMessage, actualException.getMessage());
        verify(mentorshipRequestRepository, times(1))
                .findLatestRequest(validDto.getRequesterId(), validDto.getReceiverId());
        verifyNoMoreInteractions(mentorshipRequestRepository);
    }
}
