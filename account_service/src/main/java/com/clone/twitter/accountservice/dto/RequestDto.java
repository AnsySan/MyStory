package com.clone.twitter.accountservice.dto;

import com.clone.twitter.accountservice.model.RequestStatus;
import com.clone.twitter.accountservice.model.RequestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RequestDto {
    @NotNull
    private Long requestId;

    @NotNull
    private Long userId;

    @NotNull
    private String lockValue;

    private boolean active;

    private Map<String, Object> inputData;

    @NotBlank
    private String details;

    @NotNull
    private Long version;

    private RequestType requestType;

    private RequestStatus requestStatus;
}