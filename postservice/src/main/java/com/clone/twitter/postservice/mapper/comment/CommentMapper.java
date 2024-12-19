package com.clone.twitter.postservice.mapper.comment;

import com.clone.twitter.postservice.dto.comment.CommentDto;
import com.clone.twitter.postservice.dto.comment.CommentToCreateDto;
import com.clone.twitter.postservice.dto.comment.CommentToUpdateDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.CommentLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.event.comment.CommentKafkaEvent;
import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;
import org.mapstruct.*;
import org.yaml.snakeyaml.events.CommentEvent;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "postId", target = "post.id")
    Comment toEntity(CommentToCreateDto commentDto);

    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    @Mapping(source = "post.id", target = "postId")
    CommentDto toDto(Comment comment);

    @Mapping(source = "comment.likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    @Mapping(source = "comment.post.id", target = "postId")
    @Mapping(source = "comment.authorId", target = "userId")
    CommentKafkaEvent toKafkaEvent(Comment comment, State state);

    @Mapping(source = "userId", target = "author.id")
    CommentRedisCache toRedisCache(CommentKafkaEvent comment);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    CommentRedisCache toRedisCache(Comment comment);

    @Mapping(target = "id", ignore = true)
    void update(CommentToUpdateDto commentDto, @MappingTarget Comment comment);

    @Named("getCountFromLikeList")
    default long getCountFromLikeList(List<CommentLike> likes) {
        return likes != null ? likes.size() : 0;
    }

}