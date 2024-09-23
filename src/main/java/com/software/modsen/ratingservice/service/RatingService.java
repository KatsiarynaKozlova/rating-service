package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    RatingResponse getRatingById(Long id);

    RatingListResponse getAllRatings();

    Double getAverageRatingById(Long id);

    void deleteRatingById(Long id);

    RatingResponse initRating(Long id);

    RatingResponse createRating(RatingRequest ratingRequest);

    RatingResponse updateRating(Long id, RatingRequest ratingRequest);
}
