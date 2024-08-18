package com.clone.twitter.postservice.validator;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.clone.twitter.postservice.config.S3Config;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceValidator {

    private final ResourceRepository resourceRepository;
    private final S3Config s3Config;

    @Transactional
    public void validateCountFilesPerPost(int postId, int filesToAdd) {
        if (resourceRepository.countAllByPost_Id(postId) + filesToAdd > s3Config.getMaxFiles()) {
            throw new DataValidationException(String.format("Max files per post = %s", s3Config.getMaxFiles()));
        }
    }

    @Transactional
    public void validateExistenceByKey(String key) {
        if (!resourceRepository.existsByKey(key)) {
            throw new NotFoundException(String.format("Resource with key $s not found", key));
        }
    }

    public void validatePostAuthorAndResourceAuthor(Integer postAuthorId, Integer resourceUserId) {
        if (!postAuthorId.equals(resourceUserId)) {
            throw new NotFoundException("Mismatch postAuthorIdId and resourceUserId");
        }
    }
}