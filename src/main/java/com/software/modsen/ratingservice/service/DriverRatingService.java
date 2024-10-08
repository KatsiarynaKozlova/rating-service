package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.model.DriverRating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DriverRatingService {
    DriverRating getRatingById(Long id);

    List<DriverRating> getAllRatings();

    Double getAverageRatingById(Long id);

    void deleteRatingById(Long id);

    DriverRating initRating(Long id);

    DriverRating createRating(DriverRating driverRating, Long rideId);

    DriverRating updateRating(DriverRating driverRating, Long id);
}
