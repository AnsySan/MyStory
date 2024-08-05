package com.clone.instagram.postservice.repository;

import com.clone.instagram.postservice.entity.Post;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByAuthorId(Integer authorId);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes WHERE p.authorId = :authorId")
    List<Post> findByAuthorIdWithLikes(@Param("authorId") Integer authorId);

    @Query("SELECT p FROM Post p WHERE p.published = false AND p.deleted = false AND p.scheduledAt <= CURRENT_TIMESTAMP")
    List<Post> findReadyToPublish();

    @Query("SELECT p FROM Post p WHERE p.authorId = :authorId AND p.published = true AND p.deleted = false ORDER BY p.createdAt DESC")
    List<Post> findPublishedPostsByAuthor(Integer authorId);

    @Query("SELECT p FROM Post p WHERE p.projectId = :projectId AND p.published = true AND p.deleted = false ORDER BY p.createdAt DESC")
    List<Post> findPublishedPostsByProject(Integer projectId);
}

