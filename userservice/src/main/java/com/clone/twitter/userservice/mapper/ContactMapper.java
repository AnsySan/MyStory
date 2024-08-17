package com.clone.twitter.userservice.mapper;

import com.clone.twitter.userservice.dto.ContactDto;
import com.clone.twitter.userservice.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {

    @Mapping(target = "user.id", source = "userId")
    Contact toEntity(ContactDto contactDto);

    @Mapping(target = "userId", source = "user.id")
    ContactDto toDto(Contact contact);
}