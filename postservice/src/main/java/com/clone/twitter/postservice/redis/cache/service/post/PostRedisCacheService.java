package com.clone.twitter.postservice.redis.cache.service.post;

import com.clone.twitter.postservice.redis.cache.entity.PostRedisCache;
import com.clone.twitter.postservice.redis.cache.repository.PostRedisRepository;
import com.clone.twitter.postservice.redis.cache.service.RedisOperations;
import com.clone.twitter.postservice.redis.cache.service.author.AuthorRedisCacheServiceImpl;
import com.clone.twitter.postservice.redis.cache.service.feed.FeedRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Async("postsCacheTaskExecutor")
public class PostRedisCacheService {

    private final PostRedisRepository postRedisRepository;
    private final RedisOperations redisOperations;
    private final FeedRedisService feedRedisService;
    private final AuthorRedisCacheServiceImpl authorRedisCacheServiceImpl;

    public CompletableFuture<PostRedisCache> save(PostRedisCache entity, List<Long> subscriberIds) {

        entity = redisOperations.updateOrSave(postRedisRepository, entity, entity.getId());

        PostRedisCache finalEntity = entity;
        authorRedisCacheServiceImpl.save(entity.getAuthor());
        subscriberIds.forEach(subscriberId -> feedRedisService.addPostToFeed(finalEntity, subscriberId));

        log.info("Saved post with id {} to cache: {}", entity.getId(), entity);

        return CompletableFuture.completedFuture(entity);
    }

    public void deleteById(long postId, List<Long> subscriberIds) {

        PostRedisCache post = redisOperations.findById(postRedisRepository, postId).orElse(null);
        redisOperations.deleteById(postRedisRepository, postId);

        subscriberIds.forEach(subscriberId -> feedRedisService.deletePostFromFeed(post, subscriberId));

        log.info("Deleted post with id={} from cache", postId);
    }

    public void incrementLikes(long postId) {

        redisOperations.findById(postRedisRepository, postId).ifPresent(post -> {
            post.setLikesCount(post.getLikesCount() + 1);
            redisOperations.updateOrSave(postRedisRepository, post, postId);
        });
    }

    public void incrementViews(long postId) {

        redisOperations.findById(postRedisRepository, postId).ifPresent(post -> {
            post.setViewsCount(post.getViewsCount() + 1);
            redisOperations.updateOrSave(postRedisRepository, post, postId);
        });
    }

    public void decrementLikes(long postId) {

        redisOperations.findById(postRedisRepository, postId).ifPresent(post -> {
            post.setLikesCount(post.getLikesCount() - 1);
            redisOperations.updateOrSave(postRedisRepository, post, postId);
        });
    }
}