package com.software.modsen.ratingservice.controller;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.service.RatingService;
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
@RequestMapping("/ratings/pasengers/")
public class PassengerRatingController {
    private final RatingService passengerRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getPassengerRatingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(passengerRatingService.getRatingById(id));
    }

    @GetMapping
    public ResponseEntity<RatingListResponse> getAllPassengerRating() {
        return ResponseEntity.ok().body(passengerRatingService.getAllRatings());
    }

    @PutMapping("/passengers/{id}")
    public ResponseEntity<RatingResponse> updatePassengerRating(
            @PathVariable Long id,
            @RequestBody RatingRequest ratingRequest
    ) {
        return null;
    }

    @PostMapping("/passenger/{id}")
    public ResponseEntity<RatingResponse> createPassengerRating(@PathVariable Long id){
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/passengers/{id}")
    public void deletePassengerRating(@PathVariable Long id){
        passengerRatingService.deleteRatingById(id);
    }
}