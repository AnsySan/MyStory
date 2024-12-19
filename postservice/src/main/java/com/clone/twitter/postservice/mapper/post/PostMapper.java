package com.clone.twitter.postservice.mapper.post;

import com.clone.twitter.postservice.dto.post.PostCreateDto;
import com.clone.twitter.postservice.dto.post.PostDto;
import com.clone.twitter.postservice.dto.post.PostHashtagDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.PostLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.event.post.PostKafkaEvent;
import com.clone.twitter.postservice.kafka.event.post.PostViewKafkaEvent;
import com.clone.twitter.postservice.redis.cache.entity.PostRedisCache;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    Post toEntity(PostCreateDto postCreateDto);

    @Mapping(source = "likes", target = "likeIds", qualifiedByName = "getIdFromLike")
    PostHashtagDto toHashtagDto(Post post);

    @Mapping(source = "likeIds", target = "likes", qualifiedByName = "getLikeFromId")
    Post toEntity(PostHashtagDto post);

    @Mapping(source = "likeIds", target = "likesCount", qualifiedByName = "getCountFromList")
    PostDto toDto(PostHashtagDto post);

    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    PostDto toDto(Post post);

    @Mapping(source = "author.id", target = "authorId")
    PostDto toDto(PostRedisCache post);

    @Mapping(source = "state", target = "state")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.authorId", target = "authorId")
    @Mapping(source = "post.createdAt", target = "createdAt")
    @Mapping(source = "post.publishedAt", target = "publishedAt")
    @Mapping(source = "post.content", target = "content")
    PostKafkaEvent toKafkaEvent(Post post, State state);

    @Mapping(source = "post.id", target = "postId")
    PostViewKafkaEvent toViewKafkaEvent(Post post);

    @Mapping(source = "postId", target = "id")
    @Mapping(source = "authorId", target = "author.id")
    PostRedisCache toRedisCache(PostKafkaEvent post);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    PostRedisCache toRedisCache(Post post);

    @Named("getCountFromLikeList")
    default long getCountFromLikeList(List<PostLike> likes) {
        return likes != null ? likes.size() : 0;
    }

    @Named("getCountFromList")
    default long getCountFromList(List<Long> ids) {
        return ids != null ? ids.size() : 0;
    }

    @Named("getIdFromLike")
    default long getIdFromLike(PostLike like) {
        return like != null ? like.getId() : 0;
    }

    @Named("getLikeFromId")
    default PostLike getLikeFromId(Long id) {
        return PostLike.builder().id(id).build();
    }
}