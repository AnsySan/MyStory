package com.clone.twitter.userservice.mapper;


import com.clone.twitter.userservice.dto.UserDto;
import com.clone.twitter.userservice.entity.Country;
import com.clone.twitter.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(target = "preferredContact", source = "contactPreference.preference")
    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);

    @Mapping(source = "countryId", target = "country")
    @Mapping(target = "contactPreference", source = "preferredContact")
    User toEntity(UserDto userDto);

    @Named("mapToIds")
    default List<Long> mapToIds(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream().map(User::getId).toList();
    }

    default Country map(Long countryId) {
        Country country = new Country();
        country.setId(countryId);
        return country;
    }

}