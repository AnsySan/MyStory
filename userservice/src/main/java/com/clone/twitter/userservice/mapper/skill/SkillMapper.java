package com.clone.twitter.userservice.mapper.skill;

import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.model.skill.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {

    SkillDto toDto(Skill skill);

    Skill toEntity(SkillDto skillDto);

    void update(SkillDto dto, @MappingTarget Skill entity);
}