package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.mapper.RatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.RatingService;
import com.software.modsen.ratingservice.service.RideService;
import com.software.modsen.ratingservice.util.Constants;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverRatingService implements RatingService {
    private final DriverRatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RideService rideService;

    private DriverRating getByIdOrElseThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(ExceptionMessages.RATING_NOT_FOUND_EXCEPTION));
    }

    @Override
    public RatingResponse getRatingById(Long id) {
        return ratingMapper.toRatingResponse(getByIdOrElseThrow(id));
    }

    @Override
    public RatingListResponse getAllRatings() {
        return new RatingListResponse(ratingRepository.findAll().stream()
                .map(ratingMapper::toRatingResponse)
                .collect(Collectors.toList()));
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
    public RatingResponse initRating(Long id) {
        DriverRating rating = new DriverRating();
        rating.setDriverId(id);
        rating.setRate(Constants.DEFAULT_RATE);
        return ratingMapper.toRatingResponse(ratingRepository.save(rating));
    }

    @Override
    public RatingResponse createRating(RatingRequest ratingRequest) {
        RideResponse rideResponse = rideService.getRideById(ratingRequest.getRideId());
        DriverRating rating = new DriverRating();
        rating.setDriverId(rideResponse.getDriverId());
        rating.setPassengerId(rideResponse.getPassengerId());
        rating.setRate(ratingRequest.getRate());
        rating.setComment(ratingRequest.getComment());
        return ratingMapper.toRatingResponse(ratingRepository.save(rating));
    }

    @Override
    public RatingResponse updateRating(Long id, RatingRequest ratingRequest) {
        DriverRating driverRating = getByIdOrElseThrow(id);
        driverRating.setRate(ratingRequest.getRate());
        driverRating.setComment(ratingRequest.getComment());
        return ratingMapper.toRatingResponse(ratingRepository.save(driverRating));
    }
}
