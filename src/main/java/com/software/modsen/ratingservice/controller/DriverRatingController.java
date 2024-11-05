package com.software.modsen.ratingservice.controller;

import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class DriverRatingController {
    private final DriverRatingService driverRatingService;
    private final RatingMapper ratingMapper;

    @GetMapping("/{id}/drivers")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        DriverRating rating = driverRatingService.getRatingById(id);
        RatingResponse ratingResponse = ratingMapper.toRatingResponse(rating);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/drivers")
    public ResponseEntity<RatingListResponse> getAllDriverRating() {
        List<DriverRating> driverRatingList = driverRatingService.getAllRatings();
        List<RatingResponse> driverRatingResponseList = ratingMapper.toRatingResponseListFromDriverList(driverRatingList);
        RatingListResponse ratingListResponse = new RatingListResponse(driverRatingResponseList);
        return ResponseEntity.ok(ratingListResponse);
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<DriverRatingResponse> getDriverRatingById(@PathVariable String id) {
        double rate = driverRatingService.getAverageRatingById(id);
        DriverRatingResponse driverRatingResponse = new DriverRatingResponse(id, rate);
        return ResponseEntity.ok().body(driverRatingResponse);
    }

    @PutMapping("/drivers/{id}")
    public ResponseEntity<RatingResponse> updateDriverRating(
            @PathVariable Long id,
            @RequestBody RatingRequest ratingRequest
    ) {
        DriverRating newDriverRating = ratingMapper.toDriverRating(ratingRequest);
        DriverRating updatedDriverRating = driverRatingService.updateRating(newDriverRating, id);
        RatingResponse driverRatingResponse = ratingMapper.toRatingResponse(updatedDriverRating);
        return ResponseEntity.ok(driverRatingResponse);
    }

    @PostMapping("/drivers/{id}/init")
    public ResponseEntity<RatingResponse> initDriverRating(@PathVariable String id) {
        DriverRating newDriverRating = driverRatingService.initRating(id);
        RatingResponse driverRatingResponse = ratingMapper.toRatingResponse(newDriverRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(driverRatingResponse);
    }

    @PostMapping("/drivers")
    public ResponseEntity<RatingResponse> createDriverRating(
            @RequestBody RatingRequest ratingRequest
    ) {
        DriverRating newDriverRating = ratingMapper.toDriverRating(ratingRequest);
        DriverRating driverRating = driverRatingService.createRating(newDriverRating, ratingRequest.getRideId());
        RatingResponse driverRatingResponse = ratingMapper.toRatingResponse(driverRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(driverRatingResponse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/drivers/{id}")
    public void deleteDriverRating(@PathVariable Long id) {
        driverRatingService.deleteRatingById(id);
    }
}
