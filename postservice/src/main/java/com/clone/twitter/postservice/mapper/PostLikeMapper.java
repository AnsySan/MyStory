package com.clone.twitter.postservice.mapper;

import com.clone.twitter.postservice.dto.PostLikeDto;
import com.clone.twitter.postservice.entity.PostLike;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.event.like.PostLikeKafkaEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostLikeMapper {

    @Mapping(source = "postId", target = "post.id")
    PostLike toEntity(PostLikeDto likeDto);

    @Mapping(source = "post.id", target = "postId")
    PostLikeDto toDto(PostLike like);

    @Mapping(source = "like.post.id", target = "postId")
    PostLikeKafkaEvent toKafkaEvent(PostLike like, State state);
}