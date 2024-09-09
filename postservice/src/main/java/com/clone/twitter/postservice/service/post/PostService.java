package com.clone.twitter.postservice.service.post;

import com.clone.twitter.postservice.dto.PostDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.kafka.event.State;

import java.util.List;

public interface PostService {

    public PostDto findById(long id);

    public PostDto createPost(PostDto postDto);

    public PostDto publishPost(long postId);

    public PostDto updatePost(long postId, PostDto postDto);

    public PostDto deletePost(long postId);

    public List<PostDto> findPostDraftsByUserAuthorId(long id);

    public List<PostDto> findPostPublicationsByUserAuthorId(long id);

    public PostDto getPostById(long postId);

}
