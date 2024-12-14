package com.clone.twitter.userservice.dto.event;

import com.clone.twitter.userservice.dto.skill.SkillDto;
import com.clone.twitter.userservice.model.event.EventStatus;
import com.clone.twitter.userservice.model.event.EventType;
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
public class EventFilterDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<SkillDto> relatedSkills;

    @Size(max = 128, message = "location should be less than 129 symbols")
    private String location;

    private EventType type;
    private EventStatus status;
}