package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RateNotValidException;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.DriverRatingService;
import com.software.modsen.ratingservice.service.RideService;
import com.software.modsen.ratingservice.util.DefaultValues;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import com.software.modsen.ratingservice.util.LogInfoMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverRatingServiceImpl implements DriverRatingService {
    private final DriverRatingRepository ratingRepository;
    private final RideService rideService;

    private DriverRating getByIdOrElseThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(ExceptionMessages.RATING_NOT_FOUND_EXCEPTION));
    }

    @Override
    public DriverRating getRatingById(Long id) {
        DriverRating driverRating = getByIdOrElseThrow(id);
        log.info(String.format(LogInfoMessages.GET_DRIVER_RATING, driverRating.getId()));
        return driverRating;
    }

    @Override
    public List<DriverRating> getAllRatings() {
        List<DriverRating> driverRatingList = ratingRepository.findAll();
        log.info(LogInfoMessages.GET_ALL_RATINGS_DRIVERS);
        return driverRatingList;
    }

    @Override
    public Double getAverageRatingById(Long id) {
        Double rate = ratingRepository.findAverageDriverRatingByDriverId(id);
        log.info(String.format(LogInfoMessages.GET_AVERAGE_DRIVER_RATING, id));
        return rate;
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
        log.info(String.format(LogInfoMessages.DELETE_DRIVER_RATING, id));
    }

    @Override
    public DriverRating initRating(Long id) {
        DriverRating rating = new DriverRating();
        rating.setDriverId(id);
        rating.setRate(DefaultValues.DEFAULT_RATE);
        DriverRating driverRating = ratingRepository.save(rating);
        log.info(String.format(LogInfoMessages.INIT_DRIVER_RATING, driverRating.getDriverId()));
        return driverRating;
    }

    @Override
    public DriverRating createRating(DriverRating driverRating, Long rideId) {
        RideResponse rideResponse = rideService.getRideById(rideId);
        validateRate(driverRating.getRate());
        driverRating.setDriverId(rideResponse.getDriverId());
        driverRating.setPassengerId(rideResponse.getPassengerId());
        DriverRating newDriverRating = ratingRepository.save(driverRating);
        log.info(String.format(LogInfoMessages.CREATE_DRIVER_RATING, newDriverRating.getId()));
        return newDriverRating;
    }

    @Override
    public DriverRating updateRating(DriverRating driverRating, Long id) {
        DriverRating rating = getByIdOrElseThrow(id);
        validateRate(driverRating.getRate());
        rating.setRate(driverRating.getRate());
        rating.setComment(driverRating.getComment());
        DriverRating updatedRating = ratingRepository.save(rating);
        log.info(String.format(LogInfoMessages.UPDATE_DRIVER_RATING, id));
        return updatedRating;
    }

    private void validateRate(double rate) {
        if (rate > 5 || rate < 1) {
            throw new RateNotValidException(ExceptionMessages.RATE_NOT_VALID_EXCEPTION);
        }
    }
}
