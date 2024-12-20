package com.clone.twitter.postservice.validator.comment;

import com.clone.twitter.postservice.entity.Comment;

public interface CommentValidator {

    void validateCreateComment(long userId);
    void validateUpdateAlbum(Comment commentToUpdate, long userId) ;
    void validateDeleteAlbum(long postId, long userId, Comment comment) ;
}
