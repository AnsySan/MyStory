package com.clone.instagram.postservice.client;

import com.clone.instagram.postservice.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignUserInterceptor implements RequestInterceptor {

    private final UserContext userContext;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("instagram-user-id", String.valueOf(userContext.getUserId()));
    }
}
