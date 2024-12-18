package com.clone.twitter.userservice.service.skill;

import com.clone.twitter.userservice.dto.skill.SkillCandidateDto;
import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.mapper.skill.SkillCandidateMapper;
import com.clone.twitter.userservice.mapper.skill.SkillMapper;
import com.clone.twitter.userservice.model.skill.Skill;
import com.clone.twitter.userservice.model.skill.SkillOffer;
import com.clone.twitter.userservice.repository.skill.SkillOfferRepository;
import com.clone.twitter.userservice.repository.skill.SkillRepository;
import com.clone.twitter.userservice.repository.user.UserSkillGuaranteeRepository;
import com.clone.twitter.userservice.validator.skill.SkillValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final SkillValidator skillValidator;

    private final SkillOfferRepository skillOfferRepository;
    private final UserSkillGuaranteeRepository userSkillGuaranteeRepository;
    private final SkillCandidateMapper skillCandidateMapper;
    private final int MIN_SKILL_OFFERS = 3;

    public SkillDto create(SkillDto skill) {
        skillValidator.validateSkill(skill);
        Skill convertedSkill = skillMapper.toEntity(skill);
        return skillMapper.toDto(skillRepository.save(convertedSkill));
    }

    public List<SkillDto> getUserSkills(long userId) {
        return skillRepository.findAllByUserId(userId).
                stream().
                map(skillMapper::toDto).
                toList();
    }

    public List<SkillCandidateDto> getOfferedSkills(long userId) {
        return skillRepository.findSkillsOfferedToUser(userId).stream().
                collect(Collectors.groupingBy(skill -> skill, Collectors.counting())).
                entrySet().
                stream().
                map(entry -> skillCandidateMapper.toDto(entry.getKey(), entry.getValue())).
                toList();
    }

    public SkillDto acquireSkillFromOffers(long skillId, long userId) {
        if (!skillValidator.validateSkill(skillId, userId)) {
            return null;
        }
        List<SkillOffer> skillOffers = skillOfferRepository.findAllOffersOfSkill(skillId, userId);
        if (skillOffers.size() >= MIN_SKILL_OFFERS) {
            skillRepository.assignSkillToUser(skillId, userId);
            for (SkillOffer skillOffer : skillOffers) {
                long guarantorId = skillOffer.getRecommendation().getAuthor().getId();
                userSkillGuaranteeRepository.assignSkillGuaranteeToUser(skillId, userId, guarantorId);
            }
        }
        return skillMapper.toDto(skillRepository.getSkillById(skillId));
    }
}