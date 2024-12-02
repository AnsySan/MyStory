package com.clone.twitter.postservice.validator.resource;

import com.clone.twitter.postservice.config.cloud.S3Config;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.exception.NotFoundException;
import com.clone.twitter.postservice.repository.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceValidatorImpl implements ResourceValidator {

    private final ResourceRepository resourceRepository;
    private final S3Config s3Config;

    @Override
    @Transactional
    public void validateCountFilesPerPost(Long postId, int filesToAdd) {
        if (resourceRepository.countAllByPost_Id(postId) + filesToAdd > s3Config.getMaxFiles()) {
            throw new DataValidationException(String.format("Max files per post = %s", s3Config.getMaxFiles()));
        }
    }

    @Override
    @Transactional
    public void validateExistenceByKey(String key) {
        if (!resourceRepository.existsByKey(key)) {
            throw new NotFoundException(String.format("Resource with key $s not found", key));
        }
    }
    @Override
    public void validatePostAuthorAndResourceAuthor(Long postAuthorId, Long resourceUserId) {
        if (!postAuthorId.equals(resourceUserId)) {
            throw new NotFoundException("Mismatch postAuthorIdId and resourceUserId");
        }
    }
}