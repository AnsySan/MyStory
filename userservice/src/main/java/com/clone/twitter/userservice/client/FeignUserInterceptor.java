package com.clone.twitter.userservice.client;

import com.clone.twitter.userservice.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignUserInterceptor implements RequestInterceptor {

    private final UserContext userContext;

    @Override
    public void apply(RequestTemplate template) {
        template.header("twitter-user-id", String.valueOf(userContext.getUserId()));
    }
}