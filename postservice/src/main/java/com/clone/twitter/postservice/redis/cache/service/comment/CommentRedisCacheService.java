package com.clone.twitter.postservice.redis.cache.service.comment;

import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;

import java.util.concurrent.CompletableFuture;

public interface CommentRedisCacheService {

    CompletableFuture<CommentRedisCache> save(CommentRedisCache entity);

    void incrementLikes(long commentId);

    void decrementLikes(long commentId);

    void deleteById(long postId);
}
