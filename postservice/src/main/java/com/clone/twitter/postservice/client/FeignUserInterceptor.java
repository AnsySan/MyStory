package com.clone.twitter.postservice.client;

import com.clone.twitter.postservice.config.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignUserInterceptor implements RequestInterceptor {

    private final UserContext userContext;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("twitter-user-id", String.valueOf(userContext.getUserId()));
    }
}
