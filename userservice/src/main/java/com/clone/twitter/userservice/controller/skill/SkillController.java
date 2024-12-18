package com.clone.twitter.userservice.controller.skill;

import com.clone.twitter.userservice.dto.skill.SkillCandidateDto;
import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.service.skill.SkillService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class SkillController {
    private final SkillService skillService;

    public SkillDto create(@Valid SkillDto skill) {
        return skillService.create(skill);
    }

    public List<SkillDto> getUserSkills(@Positive long userId) {
        return skillService.getUserSkills(userId);
    }

    public List<SkillCandidateDto> getOfferedSkills(@Positive long userId) {
        return skillService.getOfferedSkills(userId);
    }

    public SkillDto acquireSkillFromOffers(@Positive long skillId, @Positive long userId) {
        return skillService.acquireSkillFromOffers(skillId, userId);
    }
}