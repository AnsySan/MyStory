package com.clone.twitter.postservice.mapper;

import com.clone.twitter.postservice.dto.PostDto;
import com.clone.twitter.postservice.entity.Like;
import com.clone.twitter.postservice.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "likes", expression = "java(new ArrayList<>())")
    @Mapping(target = "published", expression = "java(false)")
    @Mapping(target = "deleted", expression = "java(false)")
    Post toEntity(PostDto postDto);

    PostDto toDto(Post post);

    List<PostDto> toDto(List<Post> posts);

    @Named("toLikeIds")
    default List<Integer> toLikeIds(List<Like> likes) {
        if (likes == null) {
            return new ArrayList<>();
        }
        return likes.stream()
                .map(Like::getId)
                .toList();
    }
}
