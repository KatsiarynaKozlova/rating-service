package com.software.modsen.ratingservice.util;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.model.DriverRating;

public final class DriverRatingTestUtil {
    private DriverRatingTestUtil(){}
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_PASSENGER_ID = "1L";
    public static final String DEFAULT_DRIVER_ID = "1L";
    public static final double DEFAULT_RATE = 5;
    public static final String DEFAULT_COMMENT = "super!";
    public static final double DEFAULT_NOT_VALID_RATE = 10;
    public static final int DEFAULT_REQUEST_RATE = 5;

    public static DriverRating getDefaultDriverRating() {
        return new DriverRating(
                DEFAULT_ID,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE,
                DEFAULT_COMMENT
        );
    }

    public static DriverRating getDefaultPreCreatedDriverRating() {
        return new DriverRating(
                null,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE,
                DEFAULT_COMMENT
        );
    }

    public static DriverRating getDefaultNotValidDriverRating() {
        return new DriverRating(
                null,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_NOT_VALID_RATE,
                DEFAULT_COMMENT
        );
    }

    public static RatingRequest getDefaultRatingRequest() {
        return new RatingRequest(
                DEFAULT_ID,
                DEFAULT_REQUEST_RATE,
                DEFAULT_COMMENT
        );
    }

    public static RatingResponse getDefaultRatingResponse() {
        return new RatingResponse(
                DEFAULT_ID,
                DEFAULT_PASSENGER_ID,
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE,
                DEFAULT_COMMENT
        );
    }

    public static DriverRatingResponse getDefaultDriverRatingResponse() {
        return new DriverRatingResponse(
                DEFAULT_DRIVER_ID,
                DEFAULT_RATE
        );
    }
}
