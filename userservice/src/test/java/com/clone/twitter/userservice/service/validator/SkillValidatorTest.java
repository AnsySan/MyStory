package com.clone.twitter.userservice.service.validator;

import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.validator.skill.SkillValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillValidatorTest {
    @Mock
    SkillValidator skillValidator;
    SkillDto skill;

    @BeforeEach
    public void init() {
        skill = new SkillDto();
        skill.setId(1L);
    }
    @Test
    public void testValidateSkillIfTitleIsNull() {
        skill.setTitle(null);
        doThrow(new DataValidationException("title doesn't exist")).when(skillValidator).validateSkill(skill);
        DataValidationException thrownException = assertThrows(DataValidationException.class, () -> skillValidator.validateSkill(skill));
        assertEquals("title doesn't exist", thrownException.getMessage());
        verify(skillValidator, timeout(1)).validateSkill(skill);
    }

    @Test
    public void testValidateSkillIfTitleIsBlank() {
        skill.setTitle(" ");
        doThrow(new DataValidationException("title is empty")).when(skillValidator).validateSkill(skill);
        DataValidationException thrownException = assertThrows(DataValidationException.class, () -> skillValidator.validateSkill(skill));
        assertEquals("title is empty", thrownException.getMessage());
        verify(skillValidator, timeout(1)).validateSkill(skill);
    }

    @Test
    public void testValidationSkillIfTitleExist() {
        skill.setTitle("Driving a car");
        doThrow(new DataValidationException("already exist")).when(skillValidator).validateSkill(skill);
        DataValidationException thrownException = assertThrows(DataValidationException.class, () -> skillValidator.validateSkill(skill));
        assertEquals("already exist", thrownException.getMessage());
        verify(skillValidator, timeout(1)).validateSkill(skill);
    }

    @Test
    public void testIfOfferedSkillExist() {
        long skillId = 1L;
        long userId = 1L;
        doThrow(new DataValidationException("this skill with id " + skillId + " already exist")).
                when(skillValidator).validateSkill(skillId, userId);
        DataValidationException thrownException = assertThrows(DataValidationException.class, () -> skillValidator.validateSkill(skillId, userId));
        assertEquals("this skill with id " + skillId + " already exist", thrownException.getMessage());
    }
}
