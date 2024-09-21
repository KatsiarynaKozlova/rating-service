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
@RequestMapping("/ratings/drivers")
public class DriverRatingController {
    private final RatingService driverRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getDriverRatingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(driverRatingService.getRatingById(id));
    }

    @GetMapping
    public ResponseEntity<RatingListResponse> getAllDriverRating() {
        return ResponseEntity.ok().body(driverRatingService.getAllRatings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateDriverRating(
            @PathVariable Long id,
            @RequestBody RatingRequest ratingRequest
    ) {
        return null;
    }

    @PostMapping("/{id}")
    public ResponseEntity<RatingResponse> createDriverRating(@PathVariable Long id){
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteDriverRating(@PathVariable Long id){
        driverRatingService.deleteRatingById(id);
    }
}
