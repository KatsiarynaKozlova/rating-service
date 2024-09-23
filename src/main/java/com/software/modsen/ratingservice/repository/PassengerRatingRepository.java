package com.software.modsen.ratingservice.repository;

import com.software.modsen.ratingservice.model.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {

}