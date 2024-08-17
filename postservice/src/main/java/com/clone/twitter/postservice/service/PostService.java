package com.clone.twitter.postservice.service;

import com.clone.twitter.postservice.dto.PostDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.exception.DataValidationException;
import com.clone.twitter.postservice.mapper.PostMapper;
import com.clone.twitter.postservice.repository.PostRepository;
import com.clone.twitter.postservice.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostValidator postValidator;
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Transactional
    public PostDto createPost(PostDto postDto) {
        postValidator.validateAuthor(postDto);
        Post post = postMapper.toEntity(postDto);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Transactional
    public PostDto publishPost(Integer postId) {
        Post post = existsPost(postId);
        postValidator.checkPostAuthorship(post);
        postValidator.isPublishedPost(post);
        post.setPublished(true);
        post.setPublishedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Transactional
    public PostDto updatePost(Integer postId, PostDto postDto) {
        Post post = existsPost(postId);
        postValidator.checkPostAuthorship(post);
        post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Transactional
    public PostDto deletePost(Integer postId) {
        Post post = existsPost(postId);
        postValidator.checkPostAuthorship(post);
        postValidator.isDeletedPost(post);
        post.setPublished(false);
        post.setDeleted(true);
        return postMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public PostDto getPostById(Integer postId) {
        return postMapper.toDto(existsPost(postId));
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByAuthorId(Integer id) {
        postValidator.validateUserExist(id);
        List<Post> posts = postRepository.findPublishedPostsByAuthor(id);
        return postMapper.toDto(posts);
    }

    protected Post existsPost(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new DataValidationException("Post with ID " + postId + " not found"));
    }
}