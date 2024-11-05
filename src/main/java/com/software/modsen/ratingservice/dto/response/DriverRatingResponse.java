package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DriverRatingResponse {
    private String driverId;
    private double rate;
}
