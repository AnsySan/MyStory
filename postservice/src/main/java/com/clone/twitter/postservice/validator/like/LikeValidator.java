package com.clone.twitter.postservice.validator.like;

import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Post;

public interface LikeValidator {

    Post validateAndGetPostToLike(long userId, long postId);

    Comment validateAndGetCommentToLike(long userId, long commentId);

    void validateUserExistence(long userId);
}