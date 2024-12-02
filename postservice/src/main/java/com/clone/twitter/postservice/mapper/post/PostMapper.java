package com.clone.twitter.postservice.mapper.post;

import com.clone.twitter.postservice.dto.post.PostDto;
import com.clone.twitter.postservice.dto.post.PostHashtagDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.PostLike;
import com.clone.twitter.postservice.entity.Resource;
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

    @Mapping(target = "likes", expression = "java(new ArrayList<>())")
    @Mapping(target = "deleted", expression = "java(false)")
    Post toEntity(PostDto postDto);

    @Mapping(source = "likes", target = "likeIds", qualifiedByName = "getIdFromLike")
    PostHashtagDto toHashtagDto(Post post);

    @Mapping(source = "likeIds", target = "likes", qualifiedByName = "getLikeFromId")
    Post toEntity(PostHashtagDto post);

    @Mapping(source = "likeIds", target = "likesCount", qualifiedByName = "getCountFromList")
    PostDto toDto(PostHashtagDto post);

    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    PostDto toDto(Post post);

    @Mapping(source = "post.resources", target = "resourceIds", qualifiedByName = "getIdFromResource")
    @Mapping(source = "post.id", target = "postId")
    PostKafkaEvent toKafkaEvent(Post post, List<Long> subscriberIds, State state);

    @Mapping(source = "post.id", target = "postId")
    PostViewKafkaEvent toViewKafkaEvent(Post post);

    @Mapping(source = "postId", target = "id")
    @Mapping(source = "authorId", target = "author.id")
    PostRedisCache toRedisCache(PostKafkaEvent post);

    @Named("getCountFromLikeList")
    default int getCountFromLikeList(List<PostLike> likes) {
        return likes != null ? likes.size() : 0;
    }

    @Named("getCountFromList")
    default int getCountFromList(List<Long> ids) {
        return ids != null ? ids.size() : 0;
    }

    @Named("getIdFromLike")
    default long getIdFromLike(PostLike like) {
        return like != null ? like.getId() : 0;
    }

    @Named("getIdFromResource")
    default long getIdFromResource(Resource resource) {
        return resource != null ? resource.getId() : 0;
    }

    @Named("getLikeFromId")
    default PostLike getLikeFromId(Long id) {
        return PostLike.builder().id(id).build();
    }
}
