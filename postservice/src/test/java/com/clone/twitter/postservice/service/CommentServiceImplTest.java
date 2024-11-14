package com.clone.twitter.postservice.service;

import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.kafka.producer.comment.CommentProducer;
import com.clone.twitter.postservice.mapper.CommentMapper;
import com.clone.twitter.postservice.dto.comment.CommentDto;
import com.clone.twitter.postservice.redis.cache.service.author.AuthorRedisCacheServiceImpl;
import com.clone.twitter.postservice.repository.comment.CommentRepository;
import com.clone.twitter.postservice.repository.post.PostRepository;
import com.clone.twitter.postservice.service.comment.CommentServiceImpl;
import com.clone.twitter.postservice.service.commonMethods.CommonServiceMethods;
import com.clone.twitter.postservice.validator.comment.CommentValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentValidatorImpl commentValidatorImpl;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommonServiceMethods commonServiceMethods;
    @Mock
    private CommentProducer commentProducer;
    @Mock
    private AuthorRedisCacheServiceImpl authorRedisCacheService;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void createComment_success() {
        long postId = 1L;
        long userId = 1L;
        CommentDto commentDto = new CommentDto();
        Post post = new Post();
        Comment comment = new Comment();

        when(commonServiceMethods.findEntityById(postRepository, postId, "Post")).thenReturn(post);
        when(commentMapper.toEntity(commentDto)).thenReturn(comment);
        doNothing().when(commentValidatorImpl).validateCreateComment(userId);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.createComment(commentDto, userId, postId);

        verify(commonServiceMethods, times(1)).findEntityById(postRepository, postId, "Post");
        verify(commentMapper, times(1)).toEntity(commentDto);
        verify(commentValidatorImpl, times(1)).validateCreateComment(userId);
        verify(commentRepository, times(1)).save(comment);

        assertEquals(commentDto, result);
    }

    @Test
    void getAllPostComments_success() {
        long postId = 1L;
        Comment comment1 = new Comment();
        comment1.setCreatedAt(LocalDateTime.now().minusDays(1));
        Comment comment2 = new Comment();
        comment2.setCreatedAt(LocalDateTime.now());
        List<Comment> comments = Arrays.asList(comment1, comment2);
        CommentDto commentDto1 = new CommentDto();
        CommentDto commentDto2 = new CommentDto();

        when(commentRepository.findAllByPostId(postId)).thenReturn(comments);
        when(commentMapper.toDto(comment1)).thenReturn(commentDto1);
        when(commentMapper.toDto(comment2)).thenReturn(commentDto2);

        List<CommentDto> result = commentService.getPostComments(postId);

        verify(commentRepository, times(1)).findAllByPostId(postId);
        verify(commentMapper, times(1)).toDto(comment1);
        verify(commentMapper, times(1)).toDto(comment2);

        assertEquals(Arrays.asList(commentDto1, commentDto2), result);
    }

    @Test
    void updateComment_success() {
        long commentId = 1L;
        long userId = 1L;
        CommentDto updatedCommentDto = new CommentDto();
        CommentDto commentDto = new CommentDto();
        Comment comment = new Comment();
        Post post = new Post();
        comment.setPost(post);

        when(commonServiceMethods.findEntityById(commentRepository, commentId, "Comment")).thenReturn(comment);
        doNothing().when(commentMapper).update(updatedCommentDto, comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.updateComment(commentDto, userId, commentId);

        verify(commonServiceMethods, times(1)).findEntityById(commentRepository, commentId, "Comment");
        verify(commentMapper, times(1)).update(updatedCommentDto, comment);
        verify(commentRepository, times(1)).save(comment);
        verify(commentMapper, times(1)).toDto(comment);

        assertEquals(commentDto, result);
    }

    @Test
    void deleteComment_success() {
        long postId = 1L;
        long commentId = 1L;
        long userId = 1L;
        Comment comment = new Comment();
        Post post = new Post();
        comment.setPost(post);
        CommentDto commentDto = new CommentDto();

        when(commonServiceMethods.findEntityById(commentRepository, commentId, "Comment")).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.deleteComment(postId, commentId, userId);

        verify(commonServiceMethods, times(1)).findEntityById(commentRepository, commentId, "Comment");
        verify(commentRepository, times(1)).deleteById(commentId);
        verify(commentMapper, times(1)).toDto(comment);

        assertEquals(commentDto, result);
    }
}