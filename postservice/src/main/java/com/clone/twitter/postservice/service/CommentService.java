package com.clone.twitter.postservice.service;

import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.event.CommentEvent;
import com.clone.twitter.postservice.mapper.CommentMapper;
import com.clone.twitter.postservice.publisher.publishers.CommentEventPublisher;
import com.clone.twitter.postservice.repository.CommentRepository;
import com.clone.twitter.postservice.validator.CommentValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;
    private final CommentValidation commentValidation;
    private final CommentEventPublisher commentEventPublisher;

    public CommentDto create(CommentDto commentDto, Integer userId) {
        commentValidation.authorExistenceValidation(userId);
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setLikes(Collections.EMPTY_LIST);
        comment.setPost(postService.existsPost(commentDto.getPostId()));
        Comment newComment = commentRepository.save(comment);
        CommentEvent event = CommentEvent.builder()
                .commentId(newComment.getId())
                .authorId(newComment.getAuthorId())
                .commentedAt(newComment.getCreatedAt())
                .postId(newComment.getPost().getId())
                .build();
        commentEventPublisher.publish(event);
        return commentMapper.toDto(newComment);
    }

    public CommentDto update(CommentDto commentDto, Integer userId) {
        commentValidation.authorExistenceValidation(userId);

        commentValidation.validateCommentExistence(commentDto.getId());

        Comment comment = commentRepository.findById(commentDto.getId()).get();
        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public List<CommentDto> getPostComments(Integer postId) {
        Post post = postService.existsPost(postId);
        List<Comment> comments = post.getComments();
        return commentMapper.toDto(comments);
    }

    public void delete(CommentDto commentDto, Integer userId) {
        commentValidation.authorExistenceValidation(userId);
        commentValidation.validateCommentExistence(commentDto.getId());
        commentRepository.deleteById(commentDto.getId());
    }

    public Comment findCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment by id: " + commentId + " not found"));
    }
}