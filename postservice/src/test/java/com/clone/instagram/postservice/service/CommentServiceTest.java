package com.clone.instagram.postservice.service;

import com.clone.instagram.postservice.dto.CommentDto;
import com.clone.instagram.postservice.entity.Comment;
import com.clone.instagram.postservice.entity.Post;
import com.clone.instagram.postservice.mapper.CommentMapper;
import com.clone.instagram.postservice.publisher.publishers.CommentEventPublisher;
import com.clone.instagram.postservice.repository.CommentRepository;
import com.clone.instagram.postservice.validator.CommentValidation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {


    @Mock
    PostService postService;

    @Mock
    private CommentMapper commentMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    CommentValidation commentValidation;
    @Mock
    CommentEventPublisher commentEventPublisher;
    @InjectMocks
    private CommentService commentService;
    CommentDto firstCommentDto;
    CommentDto secondCommentDto;

    Comment firstComment;
    Comment secondComment;
    Post post;


    @BeforeEach
    void setUp() {
        firstCommentDto = CommentDto.builder()
                .id(null)
                .content("first content")
                .authorId(1)
                .likesIds(null)
                .postId(1)
                .build();
        firstComment = Comment.builder()
                .id(1)
                .content("first content")
                .authorId(1)
                .likes(null)
                .build();
        secondCommentDto = CommentDto.builder()
                .id(2)
                .content("second content")
                .authorId(1)
                .likesIds(null)
                .build();
        secondComment = Comment.builder()
                .id(2)
                .content("edited second content")
                .authorId(1)
                .likes(null)
                .build();
        post = Post.builder()
                .id(1)
                .comments(List.of(firstComment, secondComment))
                .build();

    }

    @Test
    void testCreation() {
        Mockito.when(commentMapper.toEntity(firstCommentDto)).thenReturn(firstComment);
        Mockito.when(commentRepository.save(firstComment)).thenReturn(firstComment);
        Mockito.when(commentMapper.toDto(firstComment)).thenReturn(firstCommentDto);
        Mockito.when(postService.existsPost(firstCommentDto.getPostId())).thenReturn(new Post());

        CommentDto result = commentService.create(firstCommentDto, firstCommentDto.getAuthorId());

        Mockito.verify(commentValidation, Mockito.times(1)).authorExistenceValidation(firstCommentDto.getAuthorId());
        Mockito.verify(postService, Mockito.times(1)).existsPost(firstCommentDto.getPostId());

        Assert.assertEquals(result, firstCommentDto);
    }


    @Test
    void testUpdating() {
        Mockito.when(commentRepository.findById(secondComment.getId())).thenReturn(Optional.of(secondComment));
        Mockito.when(commentRepository.save(secondComment)).thenReturn(secondComment);
        Mockito.when(commentMapper.toDto(secondComment)).thenReturn(secondCommentDto);

        CommentDto result = commentService.update(secondCommentDto, secondCommentDto.getAuthorId());

        Mockito.verify(commentValidation, Mockito.times(1)).validateCommentExistence(secondComment.getId());
        Mockito.verify(commentValidation, Mockito.times(1)).authorExistenceValidation(secondCommentDto.getAuthorId());
        Assert.assertEquals(result, secondCommentDto);
    }

    @Test
    void testGetPostComments() {
        Mockito.when(postService.existsPost(post.getId())).thenReturn(post);

        commentService.getPostComments(post.getId());

        Mockito.verify(postService, Mockito.times(1)).existsPost(post.getId());
        Mockito.verify(commentMapper, Mockito.times(1)).toDto(post.getComments());

    }

    @Test
    void testDeletion() {
        commentService.delete(secondCommentDto, secondCommentDto.getAuthorId());

        Mockito.verify(commentValidation, Mockito.times(1)).authorExistenceValidation(secondCommentDto.getAuthorId());
        Mockito.verify(commentValidation, Mockito.times(1)).validateCommentExistence(secondComment.getId());
        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(secondComment.getId());
    }
}