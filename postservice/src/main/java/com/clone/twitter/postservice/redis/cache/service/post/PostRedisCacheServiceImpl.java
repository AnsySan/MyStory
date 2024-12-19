package com.clone.twitter.postservice.redis.cache.service.post;

import com.clone.twitter.postservice.dto.user.UserDto;
import com.clone.twitter.postservice.redis.cache.entity.PostRedisCache;
import com.clone.twitter.postservice.redis.cache.repository.PostRedisRepository;
import com.clone.twitter.postservice.redis.cache.service.RedisOperations;
import com.clone.twitter.postservice.redis.cache.service.author.AuthorRedisCacheService;
import com.clone.twitter.postservice.redis.cache.service.feed.FeedCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Async("postsCacheTaskExecutor")
public class PostRedisCacheServiceImpl implements PostRedisCacheService {

    private final PostRedisRepository postCacheRepository;
    private final RedisOperations redisOperations;
    private final FeedCacheService feedCacheService;
    private final AuthorRedisCacheService authorCacheService;

    @Override
    public void save(PostRedisCache entity) {

        entity = redisOperations.updateOrSave(postCacheRepository, entity, entity.getId());

        log.info("Saved post with id {} to cache: {}", entity.getId(), entity);

        PostRedisCache finalEntity = entity;
        CompletableFuture<UserDto> author = authorCacheService.save(entity.getAuthor());

        author.thenAccept(userDto -> userDto.getSubscriberIds()
                .forEach(subscriberId -> feedCacheService.addPostToFeed(finalEntity, subscriberId))
        );
    }

    @Override
    public void deleteById(long postId) {

        PostRedisCache post = redisOperations.findById(postCacheRepository, postId).orElse(null);

        log.info("Deleted post with id={} from cache", postId);

        if (post != null) {

            redisOperations.deleteById(postCacheRepository, postId);

            if (post.getAuthor() != null) {
                CompletableFuture<UserDto> author = authorCacheService.getUserDtoByCache(post.getAuthor());
                author.thenAccept(userDto -> userDto.getSubscriberIds()
                        .forEach(subscriberId -> feedCacheService.deletePostFromFeed(post, subscriberId))
                );
            }
        }
    }

    @Override
    public void incrementLikes(long postId) {

        redisOperations.findById(postCacheRepository, postId).ifPresent(post -> {
            post.setLikesCount(post.getLikesCount() + 1);
            redisOperations.updateOrSave(postCacheRepository, post, postId);
        });
    }

    @Override
    public void incrementViews(long postId) {

        redisOperations.findById(postCacheRepository, postId).ifPresent(post -> {
            post.setViewsCount(post.getViewsCount() + 1);
            redisOperations.updateOrSave(postCacheRepository, post, postId);
        });
    }

    @Override
    public void decrementLikes(long postId) {

        redisOperations.findById(postCacheRepository, postId).ifPresent(post -> {
            post.setLikesCount(post.getLikesCount() - 1);
            redisOperations.updateOrSave(postCacheRepository, post, postId);
        });
    }
}