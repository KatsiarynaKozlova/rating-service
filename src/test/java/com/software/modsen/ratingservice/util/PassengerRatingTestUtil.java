package com.software.modsen.ratingservice.util;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.model.PassengerRating;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PassengerRatingTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long DEFAULT_PASSENGER_ID = 1L;
    public static final Long DEFAULT_DRIVER_ID = 1L;
    public static final double DEFAULT_RATE = 5;
    public static final String DEFAULT_COMMENT = "super!";
    public static final double DEFAULT_NOT_VALID_RATE = 10;
    public static final Long DEFAULT_RIDE_ID = 1L;
    public static final int DEFAULT_REQUEST_RATE = 5;

    public PassengerRating getDeaultPassengerRating() {
        return new PassengerRating(
                DEFAULT_ID,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE,
                DEFAULT_COMMENT
        );
    }

    public PassengerRating getDefaultPreCreatedPassengerRating() {
        return new PassengerRating(
                null,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE,
                DEFAULT_COMMENT
        );
    }

    public PassengerRating getDefaultNotValidPassengerRating() {
        return new PassengerRating(
                null,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_NOT_VALID_RATE,
                DEFAULT_COMMENT
        );
    }

    public RatingRequest getDefaultRatingRequest() {
        return new RatingRequest(
                DEFAULT_RIDE_ID,
                DEFAULT_REQUEST_RATE,
                DEFAULT_COMMENT
        );
    }

    public RatingResponse getDefaultRatingResponse() {
        return new RatingResponse(
                DEFAULT_ID,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE,
                DEFAULT_COMMENT
        );
    }

    public PassengerRatingResponse getDefaultPassengerRatingResponse() {
        return new PassengerRatingResponse(
                DEFAULT_PASSENGER_ID,
                DEFAULT_RATE
        );
    }
}
