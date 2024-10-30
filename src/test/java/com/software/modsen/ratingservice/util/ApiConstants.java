package com.software.modsen.ratingservice.util;

public final class ApiConstants {
    private ApiConstants(){}

    public static final String POST_DRIVER_RATING_URL = "/ratings/drivers";
    public static final String GET_DRIVER_RATING_BY_RATING_ID_URL = "/ratings/{id}/drivers";
    public static final String GET_DRIVER_RATING_BY_DRIVER_ID_URL = "/ratings/drivers/{id}";
    public static final String PUT_DRIVER_RATING_URL = "/ratings/drivers/{id}";
    public static final String DELETE_DRIVER_RATING_URL = "/ratings/drivers/{id}";
    public static final String POST_PASSENGER_RATING_URL = "/ratings/passengers";
    public static final String GET_PASSENGER_RATING_BY_RATING_ID_URL = "/ratings/{id}/passengers";
    public static final String GET_PASSENGER_RATING_BY_PASSENGER_ID_URL = "/ratings/passengers/{id}";
    public static final String PUT_PASSENGER_RATING_URL = "/ratings/passengers/{id}";
    public static final String DELETE_PASSENGER_RATING_URL = "/ratings/passengers/{id}";
    public static final String GET_RIDE_WIRE_MOCK_URL = "/rides/1";
    public static final int RIDE_WIRE_MOCK_PORT = 8084;
}
