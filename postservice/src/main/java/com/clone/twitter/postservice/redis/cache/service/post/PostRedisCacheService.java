package com.clone.twitter.postservice.redis.cache.service.post;

import com.clone.twitter.postservice.redis.cache.entity.PostRedisCache;

public interface PostRedisCacheService {

    void save(PostRedisCache entity);

    void incrementLikes(long postId);

    void incrementViews(long postId);

    void decrementLikes(long postId);

    void deleteById(long postId);
}