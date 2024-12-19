package com.clone.twitter.postservice.redis.cache.service.comment;


import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;

public interface CommentPostCacheService {

    void tryDeleteCommentFromPost(CommentRedisCache comment);

    void tryAddCommentToPost(CommentRedisCache comment);
}
