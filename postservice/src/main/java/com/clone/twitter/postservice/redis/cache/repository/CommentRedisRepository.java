package com.clone.twitter.postservice.redis.cache.repository;

import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRedisRepository extends KeyValueRepository<CommentRedisCache, Long> {
}