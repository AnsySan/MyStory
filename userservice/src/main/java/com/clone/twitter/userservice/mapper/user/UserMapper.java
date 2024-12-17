package com.clone.twitter.userservice.mapper.user;

import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "preferredContact", source = "contactPreference.preference")
    @Mapping(source = "country.id", target = "countryId")
    UserDto toDto(User user);

    @Mapping(target = "contactPreference.preference", source = "preferredContact")
    User toEntity(UserDto userDto);
}