package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.mapper.RatingMapper;
import com.software.modsen.ratingservice.model.Rating;
import com.software.modsen.ratingservice.repository.RatingRepository;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public RatingResponse getRatingById(Long id) {
        return ratingMapper.toRatingResponse(getByIdOrElseThrow(id));
    }

    public RatingListResponse getRatingByDriverId(Long id){
        return new RatingListResponse(ratingRepository.findAllByDriverId(id).stream()
                .map(ratingMapper::toRatingResponse)
                .collect(Collectors.toList()));
    }

    public RatingListResponse getAllRatings(){
        return new RatingListResponse(ratingRepository.findAll().stream()
                .map(ratingMapper::toRatingResponse)
                .collect(Collectors.toList()));
    }

    public void deleteRatingById(Long id){
        ratingRepository.deleteById(id);
    }

    private Rating getByIdOrElseThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(String.format(ExceptionMessages.RATING_NOT_FOUND_EXCEPTION, id)));
    }
}
