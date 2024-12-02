package com.clone.twitter.postservice.mapper.comment;

import com.clone.twitter.postservice.dto.comment.CommentDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.CommentLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.event.comment.CommentKafkaEvent;
import com.clone.twitter.postservice.redis.cache.entity.AuthorRedisCache;
import com.clone.twitter.postservice.redis.cache.entity.CommentRedisCache;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    @Mapping(source = "post.id", target = "postId")
    CommentDto toDto (Comment comment);


    @Mapping(target = "post", ignore = true)
    Comment toEntity(CommentDto commentDto);


    @Mapping(source = "comment.likes", target = "likesCount", qualifiedByName = "getCountFromLikeList")
    @Mapping(source = "comment.post.id", target = "postId")
    @Mapping(source = "comment.authorId", target = "userId")
    CommentKafkaEvent toKafkaEvent(Comment comment, State state);

    @Mapping(source = "userId", target = "author.id")
    CommentRedisCache toRedisCache(CommentKafkaEvent comment);

    @Mapping(target = "id", ignore = true)
    void update(CommentDto commentDto, @MappingTarget Comment comment);

    @Named("getCountFromLikeList")
    default int getCountFromLikeList(List<CommentLike> likes) {
        return likes != null ? likes.size() : 0;
    }

}