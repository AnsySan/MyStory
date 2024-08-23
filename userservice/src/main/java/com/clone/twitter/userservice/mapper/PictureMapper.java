package com.clone.twitter.userservice.mapper;

import com.clone.twitter.userservice.dto.UserProfilePictureDto;
import com.clone.twitter.userservice.entity.UserProfilePicture;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper {

    UserProfilePictureDto toDto(UserProfilePicture userProfilePic);

    UserProfilePicture toEntity(UserProfilePictureDto userProfilePicDto);
}