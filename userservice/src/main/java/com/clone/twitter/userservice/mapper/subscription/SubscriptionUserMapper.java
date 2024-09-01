package com.clone.twitter.userservice.mapper.subscription;

import com.clone.twitter.userservice.dto.subscription.SubscriptionUserDto;
import com.clone.twitter.userservice.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SubscriptionUserMapper {
    List<SubscriptionUserDto> toDto (List<User> users);

    User toEntity(SubscriptionUserDto userDto);

    SubscriptionUserDto toDto(User user);

    List<User> toEntity(List<SubscriptionUserDto> users);

}