package com.clone.twitter.userservice.service.recommendation;


import com.clone.twitter.userservice.dto.recommendation.RecommendationDto;

import java.util.List;

public interface RecommendationService {

    RecommendationDto create(RecommendationDto recommendationDto);

    RecommendationDto updateRecommendation(RecommendationDto updated);

    RecommendationDto delete(long id);

    List<RecommendationDto> getAllUserRecommendations(long receiverId);

    List<RecommendationDto> getAllGivenRecommendations(long authorId);
}