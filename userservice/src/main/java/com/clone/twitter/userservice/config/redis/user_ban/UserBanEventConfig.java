package com.clone.twitter.userservice.config.redis.user_ban;

import com.clone.twitter.userservice.listener.user_ban.UserBanEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class UserBanEventConfig {

    @Value("${spring.data.channel.user_ban_channel.name}")
    private String userBanEventListenerTopic;

    @Bean
    public MessageListenerAdapter userBanEventListenerAdapter(UserBanEventListener listener) {
        return new MessageListenerAdapter(listener);
    }

    @Bean
    public Pair<Topic, MessageListenerAdapter> userBanEventListenerAdapterPair(
            @Qualifier("userBanEventListenerAdapter") MessageListenerAdapter messageListenerAdapter
    ) {
        return Pair.of(new ChannelTopic(userBanEventListenerTopic), messageListenerAdapter);
    }
}