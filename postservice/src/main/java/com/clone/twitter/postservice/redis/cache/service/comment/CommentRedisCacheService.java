package com.clone.twitter.postservice.redis.cache.service.comment;

import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;

public interface CommentRedisCacheService {

    void save(CommentRedisCache entity);

    void incrementLikes(long commentId);

    void decrementLikes(long commentId);

    void deleteById(long postId);
}
