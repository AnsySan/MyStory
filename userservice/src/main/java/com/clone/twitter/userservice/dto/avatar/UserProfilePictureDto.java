package com.clone.twitter.userservice.dto.avatar;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfilePictureDto {
    @NotBlank
    private String fileId;
    @NotBlank
    private String smallFileId;
}