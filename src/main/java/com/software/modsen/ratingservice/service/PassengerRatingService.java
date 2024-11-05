package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.model.PassengerRating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerRatingService {
    PassengerRating getRatingById(Long id);

    List<PassengerRating> getAllRatings();

    Double getAverageRatingById(String id);

    void deleteRatingById(Long id);

    PassengerRating initRating(String id);

    PassengerRating createRating(PassengerRating passengerRating, Long rideId);

    PassengerRating updateRating(PassengerRating passengerRating, Long id);
}
