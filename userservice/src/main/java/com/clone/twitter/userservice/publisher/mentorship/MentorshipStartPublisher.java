package com.clone.twitter.userservice.publisher.mentorship;

import com.clone.twitter.userservice.event.mentorship.MentorshipStartEvent;
import com.clone.twitter.userservice.publisher.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MentorshipStartPublisher implements MessagePublisher<MentorshipStartEvent> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic mentorshipStartTopic;

    @Override
    public void publish(MentorshipStartEvent event) {
        redisTemplate.convertAndSend(mentorshipStartTopic.getTopic(), event);
        log.info("Published mentorship start event - {}:{}", mentorshipStartTopic.getTopic(), event);
    }
}