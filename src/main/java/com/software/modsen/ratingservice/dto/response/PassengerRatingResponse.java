package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PassengerRatingResponse {
    private String passengerId;
    private double rate;
}
