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
@RequestMapping("/ratings")
public class DriverRatingController {
    private final RatingService driverRatingService;

    @GetMapping("/{id}/drivers")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(driverRatingService.getRatingById(id));
    }

    @GetMapping("/drivers")
    public ResponseEntity<RatingListResponse> getAllDriverRating() {
        return ResponseEntity.ok().body(driverRatingService.getAllRatings());
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<Double> getDriverRatingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(driverRatingService.getAverageRatingById(id));
    }

    @PutMapping("/drivers/{id}")
    public ResponseEntity<RatingResponse> updateDriverRating(
            @PathVariable Long id,
            @RequestBody RatingRequest ratingRequest
    ) {
        return ResponseEntity.ok().body(driverRatingService.updateRating(id, ratingRequest));
    }

    @PostMapping("/drivers/{id}/init")
    public ResponseEntity<RatingResponse> initDriverRating(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRatingService.initRating(id));
    }

    @PostMapping("/drivers")
    public ResponseEntity<RatingResponse> createDriverRating(
            @RequestBody RatingRequest ratingRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRatingService.createRating(ratingRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/drivers/{id}")
    public void deleteDriverRating(@PathVariable Long id) {
        driverRatingService.deleteRatingById(id);
    }
}
