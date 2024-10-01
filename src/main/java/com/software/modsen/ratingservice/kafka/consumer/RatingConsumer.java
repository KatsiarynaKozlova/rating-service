package com.software.modsen.ratingservice.kafka.consumer;

import com.software.modsen.ratingservice.dto.request.DriverForRating;
import com.software.modsen.ratingservice.dto.request.PassengerForRating;
import com.software.modsen.ratingservice.service.DriverRatingService;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import com.software.modsen.ratingservice.util.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingConsumer {
    private final DriverRatingService driverRatingService;
    private final PassengerRatingService passengerRatingService;

    @KafkaListener(topics = KafkaConstants.KAFKA_PASSENGER_IDS_TOPIC,
            properties = {"spring.json.value.default.type=com.software.modsen.ratingservice.dto.request.PassengerForRating"})
    public void consume(PassengerForRating passenger) {
        passengerRatingService.initRating(passenger.id());
    }

    @KafkaListener(topics = KafkaConstants.KAFKA_DRIVER_IDS_TOPIC,
            properties = {"spring.json.value.default.type=com.software.modsen.ratingservice.dto.request.DriverForRating"})
    public void consume(DriverForRating driver) {
        driverRatingService.initRating(driver.id());
    }
}
