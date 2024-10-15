package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RatingListResponse {
    private List<RatingResponse> ratings;
}
