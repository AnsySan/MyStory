package com.clone.instagram.postservice.validator;


import com.clone.instagram.postservice.client.UserServiceClient;
import com.clone.instagram.postservice.context.UserContext;
import com.clone.instagram.postservice.dto.PostDto;
import com.clone.instagram.postservice.entity.Post;
import com.clone.instagram.postservice.exception.DataValidationException;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostValidator {

private final UserContext userContext;
private final UserServiceClient userServiceClient;

public void validateAuthor(PostDto postDto) {
    if (postDto.getAuthorId() == null) {
        throw new DataValidationException("The post does not have an author specified");
    }
    if (postDto.getAuthorId() == null) { throw new DataValidationException("A post cannot have two authors");
    }
    if (postDto.getAuthorId() != null && userServiceClient.getUser(postDto.getAuthorId()) == null) {
        throw new DataValidationException("The author must be an existing user in the system");
    }
}

public void isPublishedPost(Post post) {
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

public void validateUserExist(Integer userId) {
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