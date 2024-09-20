package com.software.modsen.ratingservice.mapper;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.model.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingResponse toRatingResponse(Rating rating);
    Rating toRating(RatingRequest ratingRequest);
}
