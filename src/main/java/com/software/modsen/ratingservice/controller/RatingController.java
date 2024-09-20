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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ratingService.getRatingById(id));
    }

    @GetMapping
    public ResponseEntity<RatingListResponse> getAllRatings() {
        return ResponseEntity.ok().body(ratingService.getAllRatings());
    }

    @GetMapping("driver/{id}")
    public ResponseEntity<RatingListResponse> getRatingByDriverId(@PathVariable Long id) {
        return ResponseEntity.ok().body(ratingService.getRatingByDriverId(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRatingById(@PathVariable Long id){
        ratingService.deleteRatingById(id);
    }
}
