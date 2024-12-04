package com.clone.twitter.accountservice.mapper;

import com.clone.twitter.accountservice.dto.RequestDto;
import com.clone.twitter.accountservice.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {
    @Mapping(target = "requestId", source = "id")
    RequestDto toDto(Request request);

    @Mapping(target = "id", source = "requestId")
    Request toEntity(RequestDto requestDto);
}