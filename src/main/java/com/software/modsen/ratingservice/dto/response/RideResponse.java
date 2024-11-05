package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideResponse {
    private Long id;
    private String driverId;
    private String passengerId;
    private String routeStart;
    private String routeEnd;
    private BigDecimal price;
    private LocalDateTime dateTimeCreate;
    private RideStatus status;
}
