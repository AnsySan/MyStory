package com.clone.instagram.postservice.repository;

import com.clone.instagram.postservice.entity.Post;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByAuthorId(Integer authorId);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes WHERE p.authorId = :authorId")
    List<Post> findByAuthorIdWithLikes(@Param("authorId") Integer authorId);

//    @Query("SELECT p FROM Post p WHERE p.published = false AND p.deleted = false AND p.scheduledAt <= CURRENT_TIMESTAMP")
//    List<Post> findReadyToPublish();

    @Query("SELECT p FROM Post p WHERE p.authorId = :authorId AND p.published = true AND p.deleted = false ORDER BY p.createdAt DESC")
    List<Post> findPublishedPostsByAuthor(Integer authorId);

}

