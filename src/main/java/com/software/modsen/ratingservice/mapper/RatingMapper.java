package com.software.modsen.ratingservice.mapper;

import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.model.PassengerRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingResponse toRatingResponse(PassengerRating rating);
    RatingResponse toRatingResponse(DriverRating driverRating);
}
