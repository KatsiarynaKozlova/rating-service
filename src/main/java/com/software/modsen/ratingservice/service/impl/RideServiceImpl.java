package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.client.RideFeignClient;
import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.NotFoundException;
import com.software.modsen.ratingservice.exception.RideNotFoundException;
import com.software.modsen.ratingservice.exception.ServiceUnAvailableException;
import com.software.modsen.ratingservice.service.RideService;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import com.software.modsen.ratingservice.util.LogInfoMessages;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideFeignClient rideFeignClient;

    @Override
    @Retry(name = "rideFeignClient")
    @CircuitBreaker(name = "rideFeignClient", fallbackMethod = "getRideByIdFallback")
    public RideResponse getRideById(Long id) {
        log.info(String.format(LogInfoMessages.REQUEST_RIDE_INFO, id));
        RideResponse rideResponse = rideFeignClient.getRideById(id);
        log.info(String.format(LogInfoMessages.GET_RIDE, rideResponse.getId()));
        return rideResponse;
    }

    private RideResponse getRideByIdFallback(Long id, Exception exception) {
        throw new ServiceUnAvailableException(ExceptionMessages.RIDE_SERVICE_IS_NOT_AVAILABLE);
    }

    private RideResponse getRideByIdFallback(Long id, NotFoundException exception) {
        throw new RideNotFoundException(String.format(ExceptionMessages.RIDE_NOT_FOUND_EXCEPTION, id));
    }
}
