package com.clone.twitter.postservice.mapper;

import com.clone.twitter.postservice.dto.LikeDto;
import com.clone.twitter.postservice.entity.Like;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LikeMapper {
    @Mapping(target = "postId", source = "post.id")
    LikeDto toDto(Like like);

    Like toModel(LikeDto likeDto);
}