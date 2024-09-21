package com.software.modsen.ratingservice.mapper;

import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.model.PassengerRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(source = "passengerId", target = "userId")
    RatingResponse toRatingResponse(PassengerRating rating);
    @Mapping(source = "driverId", target = "userId")
    RatingResponse toRatingResponse(DriverRating driverRating);
}
