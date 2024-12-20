package com.clone.twitter.postservice.redis.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableSet;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("posts")
public class PostRedisCache implements Serializable, Comparable<PostRedisCache> {

    @Id
    private long id;

    @Reference
    @ToString.Exclude
    private NavigableSet<CommentRedisCache> comments;
    @Reference
    @ToString.Exclude
    private AuthorRedisCache author;

    private String content;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private long likesCount;
    private long viewsCount;

    @Override
    public int compareTo(PostRedisCache o) {
        return o.getPublishedAt().compareTo(this.getPublishedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostRedisCache that = (PostRedisCache) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }
}