package com.software.modsen.ratingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "passengers_ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;
    @Column(name = "passenger_id")
    private String passengerId;
    @Column(name = "driver_id")
    private String driverId;
    @Column(name = "rate")
    private double rate;
    @Column(name = "comment")
    private String comment;
}
