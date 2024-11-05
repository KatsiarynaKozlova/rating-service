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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class PassengerRatingController {
    private final PassengerRatingService passengerRatingService;
    private final RatingMapper ratingMapper;

    @GetMapping("/{id}/passengers")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        PassengerRating passengerRating = passengerRatingService.getRatingById(id);
        RatingResponse passengerRatingResponse = ratingMapper.toRatingResponse(passengerRating);
        return ResponseEntity.ok(passengerRatingResponse);
    }

    @GetMapping("/passengers")
    public ResponseEntity<RatingListResponse> getAllPassengerRating() {
        List<PassengerRating> passengerRatingList = passengerRatingService.getAllRatings();
        List<RatingResponse> passengerRatingResponseList = ratingMapper.toRatingResponseListFromPassengerList(passengerRatingList);
        RatingListResponse ratingResponseList = new RatingListResponse(passengerRatingResponseList);
        return ResponseEntity.ok(ratingResponseList);
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<PassengerRatingResponse> getPassengerRatingById(@PathVariable String id) {
        double rate = passengerRatingService.getAverageRatingById(id);
        PassengerRatingResponse passengerRatingResponse = new PassengerRatingResponse(id, rate);
        return ResponseEntity.ok(passengerRatingResponse);
    }

    @PutMapping("/passengers/{id}")
    public ResponseEntity<RatingResponse> updatePassengerRating(
            @PathVariable Long id,
            @RequestBody RatingRequest ratingRequest
    ) {
        PassengerRating newPassengerRating = ratingMapper.toPassengerRating(ratingRequest);
        PassengerRating updatedPassengerRating = passengerRatingService.updateRating(newPassengerRating, id);
        RatingResponse passengerRatingResponse = ratingMapper.toRatingResponse(updatedPassengerRating);
        return ResponseEntity.ok(passengerRatingResponse);
    }

    @PostMapping("/passengers/{id}/init")
    public ResponseEntity<RatingResponse> initPassengerRating(@PathVariable String id) {
        PassengerRating newPassengerRating = passengerRatingService.initRating(id);
        RatingResponse passengerRatingResponse = ratingMapper.toRatingResponse(newPassengerRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(passengerRatingResponse);
    }

    @PostMapping("/passengers")
    public ResponseEntity<RatingResponse> createPassengerRating(
            @RequestBody RatingRequest ratingRequest
    ) {
        PassengerRating newPassengerRating = ratingMapper.toPassengerRating(ratingRequest);
        PassengerRating passengerRating = passengerRatingService.createRating(newPassengerRating, ratingRequest.getRideId());
        RatingResponse passengerRatingResponse = ratingMapper.toRatingResponse(passengerRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(passengerRatingResponse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/passengers/{id}")
    public void deletePassengerRating(@PathVariable Long id) {
        passengerRatingService.deleteRatingById(id);
    }
}
