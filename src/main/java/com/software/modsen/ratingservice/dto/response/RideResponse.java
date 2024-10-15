package com.software.modsen.ratingservice.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideResponse {
    private Long id;
    private Long driverId;
    private Long passengerId;
    private String routeStart;
    private String routeEnd;
    private BigDecimal price;
    private LocalDateTime dateTimeCreate;
    private RideStatus status;
}
