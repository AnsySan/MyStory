package com.clone.twitter.postservice.repository.post;

import com.clone.twitter.postservice.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("""
            SELECT p FROM Post p
            LEFT JOIN FETCH p.likes
            WHERE p.authorId = :authorId AND p.published = :published AND p.deleted = :deleted
            """)
    List<Post> findByAuthorIdAndPublishedAndDeletedWithLikes(long authorId, boolean published, boolean deleted);

    void deleteAllByAuthorIdIn(List<Long> authorIds);

    List<Post> findAllByVerified(boolean isVerified);

    @Query(nativeQuery = true, value = """
            SELECT follower_id FROM subscription
            WHERE followee_id = :authorId
            """)
    List<Long> getAuthorSubscriberIds(long authorId);
}