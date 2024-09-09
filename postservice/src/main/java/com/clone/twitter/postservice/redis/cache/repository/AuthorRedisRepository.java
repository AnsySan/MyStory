package com.clone.twitter.postservice.redis.cache.repository;

import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRedisRepository extends KeyValueRepository<AuthorRedisCache, Long> {
}