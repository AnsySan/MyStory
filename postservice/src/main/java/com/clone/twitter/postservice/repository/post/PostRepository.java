package com.clone.twitter.postservice.repository.post;

import com.clone.twitter.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT p FROM Post p
            LEFT JOIN FETCH p.likes
            WHERE p.authorId = :authorId AND p.published = :published AND p.deleted = :deleted
            """)
    List<Post> findByAuthorIdAndPublishedAndDeletedWithLikes(long authorId, boolean published, boolean deleted);

    @Query("SELECT p FROM Post p WHERE p.published = false AND p.deleted = false AND p.scheduledAt <= CURRENT_TIMESTAMP")
    List<Post> findReadyToPublish();

    @Query("SELECT p FROM Post p WHERE p.isVerify = 'NOT_VERIFIED'")
    List<Post> findAllNotVerifiedPosts();

    @Query(nativeQuery = true, value = """
            SELECT follower_id FROM subscription
            WHERE followee_id = :authorId
            """)
    List<Long> getAuthorSubscriberIds(long authorId);
}