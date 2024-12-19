package com.clone.twitter.postservice.redis.cache.service.comment;

import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;
import com.clone.twitter.postservice.redis.cache.entity.PostRedisCache;
import com.clone.twitter.postservice.redis.cache.repository.PostRedisRepository;
import com.clone.twitter.postservice.redis.cache.service.RedisOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentPostCacheServiceImpl implements CommentPostCacheService {

    @Value("${spring.data.redis.cache.settings.max-post-comments-size}")
    private int maxPostCommentsSize;
    private final PostRedisRepository postRedisRepository;
    private final RedisOperations redisOperations;

    @Override
    public void tryDeleteCommentFromPost(CommentRedisCache comment) {

        getPostAndPerform(comment, (post) -> {

            NavigableSet<CommentRedisCache> comments = post.getComments();

            if (comments != null) {
                comments.remove(comment);
            }

            redisOperations.updateOrSave(postRedisRepository, post, post.getId());

            log.info("Removed comment with id={} from post cache: {}", comment.getId(), post);
        });
    }

    @Override
    public void tryAddCommentToPost(CommentRedisCache comment) {

        getPostAndPerform(comment, (post) -> {

            NavigableSet<CommentRedisCache> comments = post.getComments();

            if (comments == null) {
                comments = new TreeSet<>(Comparator.comparing(CommentRedisCache::getCreatedAt));
                comments.add(comment);
                post.setComments(comments);
            } else {
                comments.add(comment);
                while (comments.size() > maxPostCommentsSize) {
                    comments.remove(comments.last());
                }
            }

            redisOperations.updateOrSave(postRedisRepository, post, post.getId());

            log.info("Added comment with id={} to post cache: {}", comment.getId(), post);
        });
    }

    private void getPostAndPerform(CommentRedisCache comment, Consumer<PostRedisCache> consumer) {

        redisOperations.findById(postRedisRepository, comment.getPostId()).ifPresentOrElse(
                consumer,
                () -> log.warn("Post with id={} not found", comment.getPostId())
        );
    }
}