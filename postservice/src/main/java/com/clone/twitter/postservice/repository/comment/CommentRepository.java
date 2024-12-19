package com.clone.twitter.postservice.repository.comment;

import com.clone.twitter.postservice.entity.Comment;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> findAllByPostId(Long postId);

    @Query(nativeQuery = true, value = """
              SELECT * FROM comment c
              WHERE c.post_id = :postId
              ORDER BY
                  c.created_at DESC,
                  COALESCE((SELECT COUNT(*) FROM comment_likes cl WHERE cl.comment_id = c.id), 0) DESC
              LIMIT :limit
            """)
    List<Comment> findByPostIdOrderByCreatedAtAsc(@Param("postId") long postId, @Param("limit") int limit);

    List<Comment> findByVerifiedIsNull();
}