package com.clone.twitter.postservice.kafka.event.post;

import com.clone.twitter.postservice.kafka.event.KafkaEvent;
import com.clone.twitter.postservice.kafka.event.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class PostKafkaEvent implements KafkaEvent {

    private Long postId;
    private Long authorId;
    private String content;
    private List<String> resourceIds;
    private List<Long> subscriberIds;
    private State state;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime publishedAt;
}