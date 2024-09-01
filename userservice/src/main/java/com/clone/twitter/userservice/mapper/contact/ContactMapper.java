package com.clone.twitter.userservice.mapper.contact;

import com.clone.twitter.userservice.dto.contact.ContactDto;
import com.clone.twitter.userservice.model.contact.Contact;
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