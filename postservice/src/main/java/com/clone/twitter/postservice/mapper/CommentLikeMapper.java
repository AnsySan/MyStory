package com.clone.twitter.postservice.mapper;

import com.clone.twitter.postservice.dto.CommentLikeDto;
import com.clone.twitter.postservice.entity.CommentLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.event.like.CommentLikeKafkaEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentLikeMapper {

    @Mapping(source = "commentId", target = "comment.id")
    CommentLike toEntity(CommentLikeDto likeDto);

    @Mapping(source = "comment.id", target = "commentId")
    CommentLikeDto toDto(CommentLike like);

    @Mapping(source = "like.comment.id", target = "commentId")
    CommentLikeKafkaEvent toKafkaEvent(CommentLike like, State state);
}