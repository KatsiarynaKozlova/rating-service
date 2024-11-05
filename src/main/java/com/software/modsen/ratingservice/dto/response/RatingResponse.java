package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RatingResponse {
    private Long id;
    private String driverId;
    private String passengerId;
    private double rate;
    private String comment;
}
