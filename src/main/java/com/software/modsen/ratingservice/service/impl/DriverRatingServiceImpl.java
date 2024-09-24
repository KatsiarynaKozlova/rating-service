package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.mapper.RatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.DriverRatingService;
import com.software.modsen.ratingservice.service.RideService;
import com.software.modsen.ratingservice.util.Constants;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverRatingServiceImpl implements DriverRatingService {
    private final DriverRatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RideService rideService;

    private DriverRating getByIdOrElseThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(ExceptionMessages.RATING_NOT_FOUND_EXCEPTION));
    }

    @Override
    public DriverRating getRatingById(Long id) {
        return getByIdOrElseThrow(id);
    }

    @Override
    public List<DriverRating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Double getAverageRatingById(Long id) {
        return ratingRepository.findAverageDriverRatingByDriverId(id);
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public DriverRating initRating(Long id) {
        DriverRating rating = new DriverRating();
        rating.setDriverId(id);
        rating.setRate(Constants.DEFAULT_RATE);
        return ratingRepository.save(rating);
    }

    @Override
    public DriverRating createRating(DriverRating driverRating, Long rideId) {
        RideResponse rideResponse = rideService.getRideById(rideId);
        driverRating.setDriverId(rideResponse.getDriverId());
        driverRating.setPassengerId(rideResponse.getPassengerId());
        return ratingRepository.save(driverRating);
    }

    @Override
    public DriverRating updateRating(DriverRating driverRating, Long id) {
        DriverRating rating = getByIdOrElseThrow(id);
        rating.setRate(driverRating.getRate());
        rating.setComment(driverRating.getComment());
        return ratingRepository.save(driverRating);
    }
}
