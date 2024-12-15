package com.clone.twitter.userservice.mapper.mentorship;

import com.clone.twitter.userservice.dto.mentorship.MentorshipRequestDto;
import com.clone.twitter.userservice.event.mentorship.MentorshipStartEvent;
import com.clone.twitter.userservice.model.MentorshipRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MentorshipRequestMapper {

    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "receiver.id", target = "receiverId")
    MentorshipRequestDto toDto(MentorshipRequest entity);

    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    MentorshipRequest toEntity(MentorshipRequestDto dto);

    List<MentorshipRequestDto> toDtoList(List<MentorshipRequest> entities);

    @Mapping(source = "receiver.id", target = "mentorId")
    @Mapping(source = "requester.id", target = "menteeId")
    MentorshipStartEvent toEvent(MentorshipRequest request);
}