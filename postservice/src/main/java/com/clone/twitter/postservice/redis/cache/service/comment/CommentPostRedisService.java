package com.clone.twitter.postservice.redis.cache.service.comment;

import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;

public interface CommentPostRedisService {

    public void tryDeleteCommentFromPost(CommentRedisCache comment);

    public void tryAddCommentToPost(CommentRedisCache comment);
}
