package com.clone.twitter.userservice.mapper.premium;

import com.clone.twitter.userservice.model.premium.Premium;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PremiumMapper {

    @Mapping(source = "id", target = "user.id")
    Premium toEntity(Long id, LocalDateTime startDate, LocalDateTime endDate);
}