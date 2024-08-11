package com.clone.instagram.postservice.context;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private final ThreadLocal<Integer> userIdHolder = new ThreadLocal<>();

    public void setUserId(int userId) {
        userIdHolder.set(userId);
    }

    public int getUserId() {
        return userIdHolder.get();
    }

    public void clear() {
        userIdHolder.remove();
    }
}