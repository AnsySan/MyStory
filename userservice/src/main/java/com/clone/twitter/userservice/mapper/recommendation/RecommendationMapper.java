package com.clone.twitter.userservice.mapper.recommendation;

import com.clone.twitter.userservice.dto.recommendation.RecommendationDto;
import com.clone.twitter.userservice.model.recomendation.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationMapper {

    RecommendationDto toDto(Recommendation recommendation);

    Recommendation toEntity(RecommendationDto recommendationDto);
}