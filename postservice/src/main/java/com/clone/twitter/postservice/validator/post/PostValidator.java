package com.clone.twitter.postservice.validator.post;

import com.clone.twitter.postservice.entity.Post;

public interface PostValidator {

    void validateAuthor(Long userId, Long projectId);

    void validatePostContent(String content);

    void validatePublicationPost(Post post);

}