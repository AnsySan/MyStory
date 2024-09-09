package com.clone.twitter.postservice.validator.resource;

public interface ResourceValidator {

    void validateCountFilesPerPost(Long postId, int filesToAdd);

    void validateExistenceByKey(String key);

    void validatePostAuthorAndResourceAuthor(Long postAuthorId, Long resourceUserId);
}