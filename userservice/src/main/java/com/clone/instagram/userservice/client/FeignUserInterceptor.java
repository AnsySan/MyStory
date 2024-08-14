package com.clone.instagram.userservice.client;

import com.clone.instagram.userservice.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignUserInterceptor implements RequestInterceptor {

    private final UserContext userContext;

    @Override
    public void apply(RequestTemplate template) {
        template.header("telegram-user-id", String.valueOf(userContext.getUserId()));
    }
}