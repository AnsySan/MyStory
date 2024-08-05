package com.clone.instagram.postservice.mapper;

import com.clone.instagram.postservice.dto.PostDto;
import com.clone.instagram.postservice.entity.Like;
import com.clone.instagram.postservice.entity.Post;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface PostMapper {

    @Mapping(target = "likes", source = "likes", qualifiedByName = "mapLikesToDto")
    PostDto toDto(Post post);

    @Mapping(target = "likes", expression = "java(new ArrayList<>())")
    @Mapping(target = "published", expression = "java(false)")
    @Mapping(target = "deleted", expression = "java(false)")
    Post toEntity(PostDto postDto);

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
