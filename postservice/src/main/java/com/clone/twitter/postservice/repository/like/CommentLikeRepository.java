package com.clone.twitter.postservice.repository.like;

import com.clone.twitter.postservice.entity.CommentLike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLike, Integer> {

    void deleteByCommentIdAndUserId(long commentId, long userId);

}