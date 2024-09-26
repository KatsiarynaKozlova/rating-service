package com.software.modsen.ratingservice.controller;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.mapper.RatingMapper;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class PassengerRatingController {
    private final PassengerRatingService passengerRatingService;
    private final RatingMapper ratingMapper;

    @GetMapping("/{id}/passengers")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingMapper.toRatingResponse(passengerRatingService.getRatingById(id)));
    }

    @GetMapping("/passengers")
    public ResponseEntity<RatingListResponse> getAllPassengerRating() {
        return ResponseEntity.ok(new RatingListResponse(
                ratingMapper.toRatingResponseListFromPassengerList(passengerRatingService.getAllRatings())));
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<PassengerRatingResponse> getPassengerRatingById(@PathVariable Long id) {
        double rate = passengerRatingService.getAverageRatingById(id);
        return ResponseEntity.ok(new PassengerRatingResponse(id, rate));
    }

    @PutMapping("/passengers/{id}")
    public ResponseEntity<RatingResponse> updatePassengerRating(
            @PathVariable Long id,
            @RequestBody RatingRequest ratingRequest
    ) {
        PassengerRating passengerRating = ratingMapper.toPassengerRating(ratingRequest);
        return ResponseEntity.ok(ratingMapper.toRatingResponse(passengerRatingService.updateRating(passengerRating, id)));
    }

    @PostMapping("/passengers/{id}/init")
    public ResponseEntity<RatingResponse> initPassengerRating(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingMapper.toRatingResponse(passengerRatingService.initRating(id)));
    }

    @PostMapping("/passengers")
    public ResponseEntity<RatingResponse> createPassengerRating(
            @RequestBody RatingRequest ratingRequest
    ) {
        PassengerRating passengerRating = ratingMapper.toPassengerRating(ratingRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingMapper.toRatingResponse(
                        passengerRatingService.createRating(passengerRating, ratingRequest.getRideId())));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/passengers/{id}")
    public void deletePassengerRating(@PathVariable Long id) {
        passengerRatingService.deleteRatingById(id);
    }
}
