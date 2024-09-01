package com.clone.twitter.userservice.mapper.avatar;

import com.clone.twitter.userservice.dto.avatar.UserProfilePictureDto;
import com.clone.twitter.userservice.model.user.UserProfilePicture;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper {

    UserProfilePictureDto toDto(UserProfilePicture userProfilePic);

    UserProfilePicture toEntity(UserProfilePictureDto userProfilePicDto);
}