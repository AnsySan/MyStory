package com.clone.twitter.postservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.data.kafka.topics.topic-settings.post-likes.name}")
    private String postLikesName;

    @Value("${spring.data.kafka.topics.topic-settings.comment-likes.name}")
    private String commentLikesName;

    @Value("${spring.data.kafka.topics.topic-settings.posts.name}")
    private String posts;

    @Value("${spring.data.kafka.topics.topic-settings.comments.name}")
    private String comment;

    @Value("${spring.data.kafka.topics.topic-settings.post-views.name}")
    private String postViems;

    @Bean
    public NewTopic postLikesTopic() {
        return new NewTopic(postLikesName, 1, (short) 1);
    }

    @Bean
    public NewTopic commentLikesTopic() {
        return new NewTopic(commentLikesName, 1, (short) 1);
    }

    @Bean
    public NewTopic postsTopic() {
        return new NewTopic(posts, 1, (short) 1);
    }

    @Bean
    public NewTopic commentsTopic() {
        return new NewTopic(comment, 1, (short) 1);
    }

    @Bean
    public NewTopic postViemsTopic() {
        return new NewTopic(postViems, 1, (short) 1);
    }
}
