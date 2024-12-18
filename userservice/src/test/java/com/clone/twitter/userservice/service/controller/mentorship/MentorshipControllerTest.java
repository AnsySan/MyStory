package com.clone.twitter.userservice.service.controller.mentorship;

import com.clone.twitter.userservice.controller.mentorship.MentorshipController;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.service.mentorship.MentorshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MentorshipControllerTest {
    @InjectMocks
    private MentorshipController mentorshipController;

    @Mock
    private MentorshipService mentorshipService;

    @Test
    public void testGetMenteesWithGettingMentees() {
        List<UserDto> users = List.of(UserDto.builder().id(2L).build(), UserDto.builder().id(3L).build());
        when(mentorshipService.getMentees(1L)).thenReturn(users);

        List<UserDto> result = mentorshipController.getMentees(1L);
        assertEquals(result.get(0).getId(), users.get(0).getId());
        assertEquals(result.get(1).getId(), users.get(1).getId());
    }

    @Test
    public void testGetMentorsWithGettingMentors() {
        List<UserDto> users = List.of(UserDto.builder().id(2L).build(), UserDto.builder().id(3L).build());
        when(mentorshipService.getMentors(1L)).thenReturn(users);

        List<UserDto> result = mentorshipController.getMentors(1L);
        assertEquals(result.get(0).getId(), users.get(0).getId());
        assertEquals(result.get(1).getId(), users.get(1).getId());
    }

    @Test
    public void testDeleteMenteeWithDeletionMentee() {
        mentorshipController.deleteMentee(1L, 2L);
        verify(mentorshipService, times(1)).deleteMentee(1L, 2L);
    }

    @Test
    public void testDeleteMentorWithDeletionMentor() {
        mentorshipController.deleteMentor(1L, 2L);
        verify(mentorshipService, times(1)).deleteMentor(1L, 2L);
    }
}
