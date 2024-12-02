package com.clone.twitter.postservice.service.user;

import com.clone.twitter.postservice.dto.user.UserDto;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;

public interface UserService {

    UserDto getUserById(Long userId);

    AuthorRedisCache getUserAuthorCacheById(Long userId);
}