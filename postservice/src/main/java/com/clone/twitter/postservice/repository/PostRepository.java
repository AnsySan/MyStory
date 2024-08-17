package com.clone.twitter.postservice.repository;

import com.clone.twitter.postservice.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByAuthorId(Integer authorId);

    void deleteAllByAuthorIdIn(List<Integer> authorIds);


    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes WHERE p.authorId = :authorId")
    List<Post> findByAuthorIdWithLikes(Integer authorId);

    List<Post> findAllByVerified(boolean isVerified);

    @Query("SELECT p FROM Post p WHERE p.authorId = :authorId AND p.published = true AND p.deleted = false ORDER BY p.createdAt DESC")
    List<Post> findPublishedPostsByAuthor(Integer authorId);

}