package com.clone.twitter.postservice.service.comment;

import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.entity.Comment;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,long postId, long userId);

    List<CommentDto> getPostComments(long postId);

    CommentDto updateComment(CommentDto commentDto,long commentId, long userId);

    CommentDto deleteComment(long commentId, long userId,long postId);

}