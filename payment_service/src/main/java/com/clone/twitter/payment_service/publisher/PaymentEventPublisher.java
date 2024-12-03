package com.clone.twitter.payment_service.publisher;

import com.clone.twitter.payment_service.event.PaymentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher extends EventPublisher<PaymentEvent> {

    @Autowired
    public PaymentEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                 ChannelTopic paymentTopic,
                                 ObjectMapper mapper) {
        super(redisTemplate, paymentTopic, mapper);
    }
}