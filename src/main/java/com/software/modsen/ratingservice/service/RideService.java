package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.response.RideResponse;

public interface RideService {
    RideResponse getRideById(Long id);
}
