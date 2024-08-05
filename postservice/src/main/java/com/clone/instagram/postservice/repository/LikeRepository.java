package com.clone.instagram.postservice.repository;

import com.clone.instagram.postservice.entity.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends CrudRepository<Like, Integer> {

    void deleteByPostIdAndUserId(long postId, long userId);

    List<Like> findByPostId(long postId);

    Optional<Like> findByPostIdAndUserId(long postId, long userId);

}
