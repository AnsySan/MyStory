package com.clone.twitter.postservice.kafka.event.post;

import com.clone.twitter.postservice.kafka.event.KafkaEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class PostViewKafkaEvent implements KafkaEvent {

    private Long postId;
}