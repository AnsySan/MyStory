package com.clone.twitter.postservice.repository.like;

import com.clone.twitter.postservice.entity.PostLike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
    void deleteByPostIdAndUserId(long postId, long userId);

    List<PostLike> findByPostId(long postId);

    Optional<PostLike> findByPostIdAndUserId(long postId, long userId);
}
