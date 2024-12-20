package com.clone.twitter.postservice.service.comment;

import com.clone.twitter.postservice.dto.comment.CommentDto;
import com.clone.twitter.postservice.dto.comment.CommentToCreateDto;
import com.clone.twitter.postservice.dto.comment.CommentToUpdateDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, long userId, CommentToCreateDto commentDto);

    List<CommentDto> getAllPostComments(long postId);

    CommentDto updateComment(long commentId, long userId, CommentToUpdateDto commentDto);

    CommentDto deleteComment(long postId, long commentId, long userId);
}