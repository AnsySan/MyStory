package com.clone.twitter.postservice.service.like;

public interface CommentLikeService<T> {

    T addLikeToComment(long userId, long id);

    void removeLikeToComment(long userId, long id);

}