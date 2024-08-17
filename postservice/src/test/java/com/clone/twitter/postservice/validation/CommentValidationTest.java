package com.clone.twitter.postservice.validation;

import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.repository.CommentRepository;
import com.clone.twitter.postservice.validator.CommentValidation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentValidationTest {
    @Mock
    UserServiceClient userServiceClient;
    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentValidation commentValidation;

    CommentDto firstCommentDto;
    CommentDto secondCommentDto;

    Comment firstComment;
    Comment secondComment;

    @BeforeEach
    void setUp() {
        firstCommentDto = CommentDto.builder()
                .id(null)
                .content("first content")
                .authorId(1)
                .likesIds(null)
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

    }


    @Test
    public void testAuthorValidation() {
        commentValidation.authorExistenceValidation(firstCommentDto.getAuthorId());
    }

    @Test
    public void testCommentExistenceValidation() {
        Mockito.when(commentRepository.existsById(firstComment.getId())).thenReturn(true);
        commentValidation.validateCommentExistence(firstComment.getId());
    }

    @Test
    public void testCommentExistenceValidation_CommentDoesNotExist() {
        Mockito.when(commentRepository.existsById(firstComment.getId())).thenReturn(false);

        DataValidationException e = Assert.assertThrows(DataValidationException.class,
                () -> commentValidation.validateCommentExistence(firstComment.getId()));

        Assert.assertEquals(e.getMessage(), "No comment with id" + firstComment.getId()+ " found");
    }


}