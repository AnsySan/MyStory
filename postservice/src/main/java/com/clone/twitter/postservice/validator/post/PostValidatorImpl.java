package com.clone.twitter.postservice.validator.post;


import com.clone.twitter.postservice.client.ProjectServiceClient;
import com.clone.twitter.postservice.client.UserServiceClient;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.exception.NotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidatorImpl implements PostValidator {

    private final UserServiceClient userService;
    private final ProjectServiceClient projectService;

    public void validateAuthor(Long userId, Long projectId) {
        if(userId == null && projectId == null) {
            throw new DataValidationException("UserId or projectId should not be null");
        }
        if(userId != null && projectId != null) {
            throw new DataValidationException("A post cannot have two authors");
        }
        if (userId != null) {
            validateUser(userId);
        }
        if (projectId != null) {
            validateProject(projectId);
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
            throw new DataValidationException("Post already published");
        }
    }

    private void validateUser(Long userId) {
        try {
            userService.getUser(userId);
        } catch (FeignException e) {
            throw new NotFoundException(String.format("User with id %s not found", userId));
        }
    }

    private void validateProject(Long projectId) {
        try {
            projectService.getProject(projectId);
        } catch (FeignException e) {
            throw new NotFoundException(String.format("Project with id %s not found", projectId));
        }
    }
}