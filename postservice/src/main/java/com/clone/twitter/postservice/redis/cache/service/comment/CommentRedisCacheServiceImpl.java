package com.clone.twitter.postservice.redis.cache.service.comment;

import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;
import com.clone.twitter.postservice.redis.cache.repository.CommentRedisRepository;
import com.clone.twitter.postservice.redis.cache.service.RedisOperations;
import com.clone.twitter.postservice.redis.cache.service.author.AuthorRedisCacheServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Async("commentsCacheTaskExecutor")
public class CommentRedisCacheServiceImpl implements CommentRedisCacheService {

    private final CommentRedisRepository commentRedisRepository;
    private final RedisOperations redisOperations;
    private  final CommentPostCacheService commentPostRedisService;
    private final AuthorRedisCacheServiceImpl authorRedisCacheServiceImpl;

    @Override
    public void save(CommentRedisCache entity) {

        entity = redisOperations.updateOrSave(commentRedisRepository, entity, entity.getId());

        log.info("Saved comment with id {} to cache: {}", entity.getId(), entity);

        authorRedisCacheServiceImpl.save(entity.getAuthor());
        commentPostRedisService.tryAddCommentToPost(entity);
    }

    @Override
    public void deleteById(long commentId) {

        CommentRedisCache comment = redisOperations.findById(commentRedisRepository, commentId).orElse(null);
        commentPostRedisService.tryDeleteCommentFromPost(comment);
        redisOperations.deleteById(commentRedisRepository, commentId);
        log.info("Deleted comment with id={} from cache", commentId);
    }

    @Override
    public void incrementLikes(long commentId) {

        commentRedisRepository.findById(commentId).ifPresent(comment -> {
            comment.setLikesCount(comment.getLikesCount() + 1);
            commentPostRedisService.tryAddCommentToPost(comment);
            redisOperations.updateOrSave(commentRedisRepository, comment, commentId);
        });
    }

    @Override
    public void decrementLikes(long commentId) {

        redisOperations.findById(commentRedisRepository, commentId).ifPresent(comment -> {
            comment.setLikesCount(comment.getLikesCount() - 1);
            commentPostRedisService.tryAddCommentToPost(comment);
            redisOperations.updateOrSave(commentRedisRepository, comment, commentId);
        });
    }
}