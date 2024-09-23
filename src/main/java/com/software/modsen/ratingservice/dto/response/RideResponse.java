package com.software.modsen.ratingservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RideResponse {
    private Long id;
    private Long driverId;
    private Long passengerId;
    private String routeStart;
    private String routeEnd;
    private BigDecimal price;
    private LocalDateTime dateTimeCreate;
    private Status status;
}

enum Status{
    REATED,
    ACCEPTED,
    ACTIVE,
    FINISHED,
    CANCELED
}
