package com.clone.twitter.postservice.validation;

import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.validator.comment.CommentValidatorImpl;
import com.clone.twitter.postservice.validator.user.UserValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentValidatorImplTest {

    @Mock
    private UserValidatorImpl userValidatorImpl;

    @InjectMocks
    private CommentValidatorImpl commentValidatorImpl;

    @Test
    void validateCreateComment_userExists_noExceptionThrown() {
        doNothing().when(userValidatorImpl).validateUserExistence(anyLong());

        assertDoesNotThrow(() -> commentValidatorImpl.validateCreateComment(1L));

        verify(userValidatorImpl, times(1)).validateUserExistence(1L);
    }

    @Test
    void validateCreateComment_userDoesNotExist_exceptionThrown() {
        doThrow(new DataValidationException("User does not exist")).when(userValidatorImpl).validateUserExistence(anyLong());

        assertThrows(DataValidationException.class, () -> commentValidatorImpl.validateCreateComment(1L));

        verify(userValidatorImpl, times(1)).validateUserExistence(1L);
    }
}