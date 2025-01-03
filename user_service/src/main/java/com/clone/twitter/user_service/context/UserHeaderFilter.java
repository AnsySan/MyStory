package com.clone.twitter.user_service.context;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserHeaderFilter implements Filter {

    private final UserContext userContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String userId = req.getHeader("twitter-user-id");
        if (userId != null) {
            userContext.setUserId(Integer.parseInt(userId));
        }
        try {
            chain.doFilter(request, response);
        } finally {
            userContext.clear();
        }
    }
}