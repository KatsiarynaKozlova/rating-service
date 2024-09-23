package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RatingResponse {
    private Long id;
    private Long driverId;
    private Long passengerId;
    private double rate;
    private String comment;
}
