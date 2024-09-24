package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import com.software.modsen.ratingservice.service.RideService;
import com.software.modsen.ratingservice.util.Constants;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerRatingServiceImpl implements PassengerRatingService {
    private final PassengerRatingRepository ratingRepository;
    private final RideService rideService;

    private PassengerRating getByIdOrElseThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(ExceptionMessages.RATING_NOT_FOUND_EXCEPTION));
    }

    @Override
    public PassengerRating getRatingById(Long id) {
        return getByIdOrElseThrow(id);
    }

    @Override
    public List<PassengerRating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Double getAverageRatingById(Long id) {
        return ratingRepository.findAveragePassengerRatingByPassengerId(id);
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public PassengerRating initRating(Long id) {
        PassengerRating rating = new PassengerRating();
        rating.setPassengerId(id);
        rating.setRate(Constants.DEFAULT_RATE);
        return ratingRepository.save(rating);
    }

    @Override
    public PassengerRating createRating(PassengerRating passengerRating, Long rideId) {
        RideResponse rideResponse = rideService.getRideById(rideId);
        passengerRating.setPassengerId(rideResponse.getPassengerId());
        passengerRating.setDriverId(rideResponse.getDriverId());
        return ratingRepository.save(passengerRating);
    }

    @Override
    public PassengerRating updateRating(PassengerRating passengerRating, Long id) {
        PassengerRating rating = getByIdOrElseThrow(id);
        rating.setRate(passengerRating.getRate());
        rating.setComment(passengerRating.getComment());
        return ratingRepository.save(passengerRating);
    }
}
