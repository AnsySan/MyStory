package com.clone.twitter.postservice.mapper.resource;

import com.clone.twitter.postservice.dto.resource.ResourceDto;
import com.clone.twitter.postservice.entity.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    ResourceDto toDto(Resource resource);
}