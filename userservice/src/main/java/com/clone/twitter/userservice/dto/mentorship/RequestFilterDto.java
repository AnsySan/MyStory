package com.clone.twitter.userservice.dto.mentorship;

import com.clone.twitter.userservice.model.RequestStatus;
import com.clone.twitter.userservice.validator.enumvalidator.EnumValidator;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestFilterDto {

    @Size(min = 20, max = 4096, message = "description should be more then 19 and less or equal to 4096 symbols")
    private String descriptionPattern;

    @Positive
    private Long requesterIdPattern;

    @Positive
    private Long receiverIdPattern;

    @EnumValidator(enumClass = RequestStatus.class, message = "Invalid Request Status")
    private RequestStatus statusPattern;
}