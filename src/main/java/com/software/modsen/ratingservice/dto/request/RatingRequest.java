package com.software.modsen.ratingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@Getter
@Setter
public class RatingRequest {
    private Long rideId;
    @Range(min = 1, max = 5)
    private int rate;
    private String comment;
}
