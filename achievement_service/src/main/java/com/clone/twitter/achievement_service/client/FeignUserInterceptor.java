package com.clone.twitter.achievement_service.client;

import com.clone.twitter.achievement_service.context.UserContext;
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