package com.clone.twitter.userservice.service.avatar;

import com.clone.twitter.userservice.dto.avatar.UserProfilePictureDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {

    public void generateAndSetPic(UserDto user);

    public UserProfilePictureDto saveProfilePic(long userId, MultipartFile file);

    public InputStreamResource getProfilePic(long userId);

    public UserProfilePictureDto deleteProfilePic(long userId);

}
