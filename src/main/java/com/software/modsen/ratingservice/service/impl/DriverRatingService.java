package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.mapper.RatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.RatingService;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverRatingService implements RatingService {
    private final DriverRatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

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
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public RatingResponse createRating(Long id) {
        return null;
    }

    @Override
    public RatingResponse updateRating(Long id, RatingRequest ratingRequest) {
        return null;
    }
}
