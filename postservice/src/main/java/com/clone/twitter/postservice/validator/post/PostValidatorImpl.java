package com.clone.twitter.postservice.validator.post;


import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.config.context.UserContext;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.exception.DataValidationException;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostValidatorImpl implements PostValidator {

private final UserContext userContext;
private final UserServiceClient userServiceClient;

@Override
public void validateAuthor(Long userId) {
    if (userId == null) {
        throw new DataValidationException("The post does not have an author specified");
    }
    if (userId == null) {
        throw new DataValidationException("A post cannot have two authors");
    }
    if (userId != null && userServiceClient.getUser(userId) == null) {
        throw new DataValidationException("The author must be an existing user in the system");
    }
    if (userId != null) {
        validateUser(userId);
    }
}

@Override
public void validatePostContent(String content) {
    if (content == null || content.isEmpty()) {
        throw new DataValidationException("Content should not be empty");
    }
}

@Override
public void validatePublicationPost(Post post) {
    if (post.isPublished()) {
        throw new DataValidationException("The post cannot publish that has already been published before");
    }
}

public void isDeletedPost(Post post) {
    if (post.isDeleted()) {
        throw new DataValidationException("The post cannot delete that has already been deleted before");
    }
}

public void checkPostAuthorship(Post post) {
    if ((post.getAuthorId() != null && post.getAuthorId() != getContextUserId()))
    {
        throw new DataValidationException("You are not the author of this post or the project does not match");
    }
}

public void validateUser(Long userId) {
    try {
        userServiceClient.getUser(userId);
    } catch (FeignException e) {
        throw new EntityNotFoundException("This user is not found");
    }
}

private long getContextUserId() {
    return userContext.getUserId();
    }
}