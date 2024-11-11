package com.software.modsen.ratingservice.util;

import com.software.modsen.ratingservice.dto.response.RideResponse;

public final class RideTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long DEFAULT_PASSENGER_ID = 1L;
    public static final Long DEFAULT_DRIVER_ID = 1L;

    public static RideResponse getDefaultRideResponse(){
        return RideResponse.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .build();
    }
}
