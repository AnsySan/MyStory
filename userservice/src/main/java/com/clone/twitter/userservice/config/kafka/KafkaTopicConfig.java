package com.clone.twitter.userservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.data.channels.profile_view_channel.name}")
    private String profileViewChannel;

    @Bean
    public NewTopic commentEventTopic() {
        return new NewTopic(profileViewChannel, 1, (short) 1);
    }
}