package com.software.modsen.ratingservice.repository;

import com.software.modsen.ratingservice.model.DriverRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {
    @Query("SELECT AVG(d.rate) FROM DriverRating d WHERE d.driverId = :id")
    double findAverageDriverRatingByDriverId(Long id);
}
