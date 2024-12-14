package com.clone.twitter.userservice.dto.mentorship;

import com.clone.twitter.userservice.model.RequestStatus;
import com.clone.twitter.userservice.validator.enumvalidator.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorshipRequestDto {
    private Long id;

    @NotBlank
    @Size(min = 20, max = 4096, message = "description should be more then 19 and less or equal to 4096 symbols")
    private String description;

    @Positive
    @NotNull
    private Long requesterId;

    @Positive
    @NotNull
    private Long receiverId;

    @NotNull
    @EnumValidator(enumClass = RequestStatus.class, message = "Invalid Request Status")
    private RequestStatus status;

    @Size(min = 20, max = 4096, message = "description should be more then 19 and less or equal to 4096 symbols")
    private String rejectionReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}