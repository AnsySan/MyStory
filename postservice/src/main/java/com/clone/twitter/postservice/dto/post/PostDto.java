package com.clone.twitter.postservice.dto.post;

import com.clone.twitter.postservice.dto.comment.CommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostDto {
    private long id;
    private String content;
    private Long authorId;
    private Long projectId;
    private boolean published;
    private LocalDateTime publishedAt;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;
    private long viewsCount;
    private List<CommentDto> comments;
}