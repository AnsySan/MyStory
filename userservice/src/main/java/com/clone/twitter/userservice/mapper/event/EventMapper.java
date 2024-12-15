package com.clone.twitter.userservice.mapper.event;

import com.clone.twitter.userservice.dto.event.EventDto;
import com.clone.twitter.userservice.mapper.skill.SkillMapper;
import com.clone.twitter.userservice.model.event.Event;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = SkillMapper.class)
public interface EventMapper {
    @Mapping(source = "owner.id", target = "ownerId")
    EventDto toDto(Event event);

    @Mapping(source = "ownerId", target = "owner.id")
    Event toEntity(EventDto eventDto);
}