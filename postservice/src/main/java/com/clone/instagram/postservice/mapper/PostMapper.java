package com.clone.instagram.postservice.mapper;

import com.clone.instagram.postservice.dto.PostDto;
import com.clone.instagram.postservice.entity.Like;
import com.clone.instagram.postservice.entity.Post;
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

//    @Mapping(source = "likes", target = "likeIds", qualifiedByName = "toLikeIds")
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
