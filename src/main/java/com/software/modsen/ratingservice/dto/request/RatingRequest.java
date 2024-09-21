package com.software.modsen.ratingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RatingRequest {
    private Long rideId;
    private int rate;
    private String comment;
}
