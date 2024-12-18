package com.clone.twitter.userservice.validator.event;

import com.clone.twitter.userservice.dto.event.EventDto;
import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.exception.EntityNotFoundException;
import com.clone.twitter.userservice.model.event.EventStatus;
import com.clone.twitter.userservice.model.event.EventType;
import com.clone.twitter.userservice.model.skill.Skill;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventValidatorImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EventValidatorImpl validator;

    private final Long id = 1L;
    private EventDto eventDto;
    private User user;

    @BeforeEach
    void init() {
        eventDto = EventDto.builder()
                .id(1L)
                .ownerId(1L)
                .title("eventDto")
                .description("desc")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .type(EventType.GIVEAWAY)
                .status(EventStatus.IN_PROGRESS)
                .build();

        List<Skill> skills = List.of(
                Skill.builder().id(1L).build(),
                Skill.builder().id(2L).build()
        );

        SkillDto skillDto1 = new SkillDto();
        SkillDto skillDto2 = new SkillDto();

        skillDto1.setId(1L);
        skillDto2.setId(2L);

        List<SkillDto> skillDtoList = List.of(
                skillDto1,
                skillDto2
        );

        user = User.builder()
                .id(id)
                .skills(skills)
                .build();
        eventDto.setRelatedSkills(skillDtoList);
    }

    @Test
    void validateNullTitleEvent() {
        eventDto.setTitle(null);

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("title can't be null", e.getMessage());
    }

    @Test
    void validateBlancTitleEvent() {
        eventDto.setTitle("");

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("title can't be blank", e.getMessage());
    }

    @Test
    void validateNullStartDateEvent() {
        eventDto.setStartDate(null);

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("start date can't be null", e.getMessage());
    }

    @Test
    void validateNoTitleEvent() {
        eventDto.setOwnerId(null);

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("event owner can't be null", e.getMessage());
    }

    @Test
    void validateNoTitleDescription() {
        eventDto.setDescription(null);

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("description can't be null", e.getMessage());
    }

    @Test
    void validateNoStatus() {
        eventDto.setStatus(null);

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("status can't be null", e.getMessage());
    }

    @Test
    void validateNoTypeEvent() {
        eventDto.setType(null);

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validate(eventDto));
        assertEquals("type can't be null", e.getMessage());
    }

    @Test
    void validateGoodEvent() {
        assertDoesNotThrow(() -> validator.validate(eventDto));
    }

    @Test
    void validateOwnersRequiredSkillsNotFoundUser() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> validator.validateOwnersRequiredSkills(eventDto));
        assertEquals("user with id=" + id + " not found", e.getMessage());
    }

    @Test
    void validateOwnersRequiredSkillsNoRequiredSkills() {
        user.setSkills(new ArrayList<>());
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        DataValidationException e = assertThrows(DataValidationException.class, () -> validator.validateOwnersRequiredSkills(eventDto));
        assertEquals("user with id=" + id + " has no enough skills to create event", e.getMessage());
    }

    @Test
    void validateOwnersRequiredSkillsGoodEventAndUser() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> validator.validateOwnersRequiredSkills(eventDto));
    }

    @Test
    void validateUserIdNonExistingUser() {
        when(userRepository.existsById(id)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> validator.validateUserId(id));
        assertEquals("user with id=" + id + " not found", e.getMessage());
    }

    @Test
    void validateUSerId() {
        when(userRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validateUserId(id));
    }
}