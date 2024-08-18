package com.clone.twitter.postservice.mapper;

import com.clone.twitter.postservice.dto.CommentDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(source = "likes", target = "likesIds", qualifiedByName = "likesToIds")
    @Mapping(source = "post.id", target = "postId")
    CommentDto toDto (Comment comment);

    List<CommentDto> toDto (List<Comment> comments);

    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toEntity(CommentDto commentDto);

    @Named("likesToIds")
    default List<Integer> likesToIds(List<Like> likes){
        return likes.stream()
                .map(like -> like.getId())
                .toList();
    }

}