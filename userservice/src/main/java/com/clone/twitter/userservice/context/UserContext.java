package com.clone.twitter.userservice.context;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private final ThreadLocal<Integer> userIdHolder = new ThreadLocal<>();

    public void setUserId(int userId) {
        userIdHolder.set(userId);
    }

    public long getUserId() {
        return userIdHolder.get();
    }

    public void clear() {
        userIdHolder.remove();
    }
}