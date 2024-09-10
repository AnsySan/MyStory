package com.clone.twitter.achievement_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CommentEvent implements Event {

    @NotNull
    private Long commentId;
    @NotNull
    private Long authorId;
    @NotNull
    private String content;
    @NotNull
    private Long postId;

    @Override
    public long getAchievementHolderId(){
        return authorId;
    }
}