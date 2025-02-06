package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.annotation.RedisCacheEvict;
import com.software.modsen.ratingservice.annotation.RedisCacheGet;
import com.software.modsen.ratingservice.annotation.RedisCachePut;
import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RateNotValidException;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import com.software.modsen.ratingservice.service.RideService;
import com.software.modsen.ratingservice.util.DefaultValues;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import com.software.modsen.ratingservice.util.LogInfoMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.software.modsen.ratingservice.util.RedisConstants.PASSENGER_RATING_VALUE;

@Slf4j
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
    @RedisCacheGet(value = PASSENGER_RATING_VALUE, key = "id")
    public PassengerRating getRatingById(Long id) {
        PassengerRating passengerRating = getByIdOrElseThrow(id);
        log.info(String.format(LogInfoMessages.GET_PASSENGER_RATING, passengerRating.getPassengerId()));
        return passengerRating;
    }

    @Override
    public List<PassengerRating> getAllRatings() {
        List<PassengerRating> passengerRatings = ratingRepository.findAll();
        log.info(LogInfoMessages.GET_ALL_RATINGS_PASSENGERS);
        return passengerRatings;
    }

    @Override
    public Double getAverageRatingById(Long id) {
        Double rate = ratingRepository.findAveragePassengerRatingByPassengerId(id);
        log.info(String.format(LogInfoMessages.GET_AVERAGE_PASSENGER_RATING, id));
        return rate;
    }

    @Override
    @RedisCacheEvict(value = PASSENGER_RATING_VALUE, key = "id")
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
        log.info(String.format(LogInfoMessages.DELETE_PASSENGER_RATING, id));
    }

    @Override
    @RedisCachePut(value = PASSENGER_RATING_VALUE, key = "result.id")
    public PassengerRating initRating(Long id) {
        PassengerRating rating = new PassengerRating();
        rating.setPassengerId(id);
        rating.setRate(DefaultValues.DEFAULT_RATE);
        PassengerRating passengerRating = ratingRepository.save(rating);
        log.info(String.format(LogInfoMessages.INIT_PASSENGER_RATING, id));
        return passengerRating;
    }

    @Override
    @RedisCachePut(value = PASSENGER_RATING_VALUE, key = "result.id")
    public PassengerRating createRating(PassengerRating passengerRating, Long rideId) {
        RideResponse rideResponse = rideService.getRideById(rideId);
        validateRate(passengerRating.getRate());
        passengerRating.setPassengerId(rideResponse.getPassengerId());
        passengerRating.setDriverId(rideResponse.getDriverId());
        PassengerRating newPassengerRating = ratingRepository.save(passengerRating);
        log.info(String.format(LogInfoMessages.CREATE_PASSENGER_RATING, newPassengerRating.getId()));
        return newPassengerRating;
    }

    @Override
    @RedisCachePut(value = PASSENGER_RATING_VALUE, key = "result.id")
    public PassengerRating updateRating(PassengerRating passengerRating, Long id) {
        PassengerRating rating = getByIdOrElseThrow(id);
        validateRate(passengerRating.getRate());
        rating.setRate(passengerRating.getRate());
        rating.setComment(passengerRating.getComment());
        PassengerRating updatedPassengerRating = ratingRepository.save(rating);
        log.info(String.format(LogInfoMessages.UPDATE_PASSENGER_RATING, updatedPassengerRating.getId()));
        return updatedPassengerRating;
    }

    private void validateRate(double rate) {
        if (rate > 5 || rate < 0) {
            throw new RateNotValidException(ExceptionMessages.RATE_NOT_VALID_EXCEPTION);
        }
    }
}
