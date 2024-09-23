package com.software.modsen.ratingservice.client;

import com.software.modsen.ratingservice.dto.response.RideResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "rideFeignClient", url = "http://localhost:8084/rides")
public interface RideFeignClient {
    @Retry(name = "rideFeignClient")
    @CircuitBreaker(name = "rideFeignClient")
    @GetMapping("/{id}")
    RideResponse getRideById(@PathVariable Long id);
}
