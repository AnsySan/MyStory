package com.clone.twitter.userservice.dto.event;

import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.model.event.EventStatus;
import com.clone.twitter.userservice.model.event.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;

    @NotBlank
    @Size(max = 64, message = "title should be less than 65 symbols")
    private String title;

    @NotBlank
    @Size(max = 4096, message = "description should be less than 4097 symbols")
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Size(max = 128, message = "location should be less than 129 symbols")
    private String location;

    @NotNull
    @Positive
    private Long ownerId;

    private List<SkillDto> relatedSkills;

    private EventType type;
    private EventStatus status;
}