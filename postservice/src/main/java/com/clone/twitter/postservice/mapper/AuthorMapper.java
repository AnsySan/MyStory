package com.clone.twitter.postservice.mapper;

import com.clone.twitter.postservice.dto.UserDto;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    @Mapping(source = "userProfilePic.fileId", target = "smallFileId")
    AuthorRedisCache toAuthorCache(UserDto userDto);
}