package com.clone.twitter.achievement_service.config;

import com.clone.twitter.achievement_service.listener.CommentEventListener;
import com.clone.twitter.achievement_service.listener.FollowerEventListener;
import com.clone.twitter.achievement_service.listener.LikeEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channels.achievement_channel.name}")
    private String a;
    @Value("${spring.data.redis.channels.comment_channel.name}")
    private String commentTopic;
    @Value("${spring.data.redis.channels.follower_channel.name}")
    private String followerTopic;
    @Value("${spring.data.redis.channels.like_channel.name}")
    private String likeTopic;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public MessageListenerAdapter commentListener(CommentEventListener commentEventListener) {
        return new MessageListenerAdapter(commentEventListener);
    }

    @Bean
    public MessageListenerAdapter followerListener(FollowerEventListener followerEventListener) {
        return new MessageListenerAdapter(followerEventListener);
    }

    @Bean
    public MessageListenerAdapter likeListener(LikeEventListener likeEventListener) {
        return new MessageListenerAdapter(likeEventListener);
    }

    @Bean
    public ChannelTopic commentEventTopic() {
        return new ChannelTopic(commentTopic);
    }

    @Bean
    public ChannelTopic followerEventTopic() {
        return new ChannelTopic(followerTopic);
    }

    @Bean
    public ChannelTopic likeEventTopic() {
        return new ChannelTopic(likeTopic);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter commentListener,
                                                        MessageListenerAdapter followerListener,
                                                        MessageListenerAdapter likeListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(commentListener, commentEventTopic());
        container.addMessageListener(followerListener, followerEventTopic());
        container.addMessageListener(likeListener, likeEventTopic());
        return container;
    }
}