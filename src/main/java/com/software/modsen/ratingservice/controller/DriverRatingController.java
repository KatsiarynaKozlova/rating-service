package com.software.modsen.ratingservice.controller;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.RatingListResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.mapper.RatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.service.DriverRatingService;
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

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class DriverRatingController {
    private final DriverRatingService driverRatingService;
    private final RatingMapper ratingMapper;

    @GetMapping("/{id}/drivers")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingMapper.toRatingResponse(driverRatingService.getRatingById(id)));
    }

    @GetMapping("/drivers")
    public ResponseEntity<RatingListResponse> getAllDriverRating() {
        return ResponseEntity.ok(new RatingListResponse(driverRatingService.getAllRatings()
                .stream()
                .map(ratingMapper::toRatingResponse)
                .collect(Collectors.toList())));
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
        DriverRating driverRating = ratingMapper.toDriverRating(ratingRequest);
        return ResponseEntity.ok(ratingMapper.toRatingResponse(driverRatingService.updateRating(driverRating, id)));
    }

    @PostMapping("/drivers/{id}/init")
    public ResponseEntity<RatingResponse> initDriverRating(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingMapper.toRatingResponse(driverRatingService.initRating(id)));
    }

    @PostMapping("/drivers")
    public ResponseEntity<RatingResponse> createDriverRating(
            @RequestBody RatingRequest ratingRequest
    ) {
        DriverRating driverRating = ratingMapper.toDriverRating(ratingRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingMapper.toRatingResponse(
                        driverRatingService.createRating(driverRating, ratingRequest.getRideId())));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/drivers/{id}")
    public void deleteDriverRating(@PathVariable Long id) {
        driverRatingService.deleteRatingById(id);
    }
}
