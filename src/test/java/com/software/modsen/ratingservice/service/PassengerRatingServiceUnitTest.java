package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RateNotValidException;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.exception.RideNotFoundException;
import com.software.modsen.ratingservice.exception.ServiceUnAvailableException;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
import com.software.modsen.ratingservice.service.impl.PassengerRatingServiceImpl;
import com.software.modsen.ratingservice.util.PassengerRatingTestUtil;
import com.software.modsen.ratingservice.util.RideTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.DEFAULT_ID;
import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.DEFAULT_RATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class PassengerRatingServiceUnitTest {
    @Mock
    private PassengerRatingRepository passengerRatingRepository;
    @Mock
    private RideService rideService;
    @InjectMocks
    private PassengerRatingServiceImpl passengerRatingService;

    @Test
    void getListOfPassengersRatings_shouldReturnList() {
        when(passengerRatingRepository.findAll()).thenReturn(Collections.emptyList());

        List<PassengerRating> passengerList = passengerRatingService.getAllRatings();

        assertNotNull(passengerList);
        assertEquals(0, passengerList.size());
        assertTrue(passengerList.isEmpty());
        verify(passengerRatingRepository, times(1)).findAll();
    }

    @Test
    void getPassengerRatingById_shouldReturnRating() {
        PassengerRating expectedRating = PassengerRatingTestUtil.getDefaultPassengerRating();
        when(passengerRatingRepository.findById(anyLong())).thenReturn(Optional.of(expectedRating));

        PassengerRating resultPassengerRating = passengerRatingService.getRatingById(DEFAULT_ID);

        assertNotNull(resultPassengerRating);
        assertEquals(expectedRating, resultPassengerRating);
        assertEquals(DEFAULT_ID, resultPassengerRating.getPassengerId());
        verify(passengerRatingRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void getPassengerRatingById_shouldThrowNotFoundException() {
        assertThrows(RatingNotFoundException.class, () -> passengerRatingService.getRatingById(DEFAULT_ID));
        verify(passengerRatingRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void getAveragePassengerRatingById_shouldReturnDouble() {
        double expectedRate = DEFAULT_RATE;
        when(passengerRatingRepository.findAveragePassengerRatingByPassengerId(anyLong())).thenReturn(expectedRate);

        Double resultRate = passengerRatingService.getAverageRatingById(DEFAULT_ID);

        assertNotNull(resultRate);
        assertEquals(expectedRate, resultRate);
        verify(passengerRatingRepository, times(1)).findAveragePassengerRatingByPassengerId(DEFAULT_ID);
    }

    @Test
    void createPassengerRating_shouldReturnPassengerRating() {
        PassengerRating newPassengerRating = PassengerRatingTestUtil.getDefaultPreCreatedPassengerRating();
        PassengerRating expectedCreatedPassengerRating = PassengerRatingTestUtil.getDefaultPassengerRating();
        RideResponse rideInfo = RideTestUtil.getDefaultRideResponse();

        when(passengerRatingRepository.save(any(PassengerRating.class))).thenReturn(expectedCreatedPassengerRating);
        when(rideService.getRideById(anyLong())).thenReturn(rideInfo);

        PassengerRating resultNewPassengerRating = passengerRatingService.createRating(newPassengerRating, DEFAULT_ID);

        assertNotNull(resultNewPassengerRating);
        assertEquals(resultNewPassengerRating, expectedCreatedPassengerRating);
        verify(rideService, times(1)).getRideById(DEFAULT_ID);
        verify(passengerRatingRepository, times(1)).save(newPassengerRating);
    }

    @Test
    void createPassengerRating_shouldReturnRideNotFoundException() {
        PassengerRating newPassengerRating = PassengerRatingTestUtil.getDefaultPreCreatedPassengerRating();

        when(rideService.getRideById(anyLong())).thenThrow(RideNotFoundException.class);
        assertThrows(
                RideNotFoundException.class,
                () -> passengerRatingService.createRating(newPassengerRating, DEFAULT_ID)
        );
        verify(rideService).getRideById(DEFAULT_ID);
    }

    @Test
    void createPassengerRating_shouldReturnRateNotValidException() {
        PassengerRating newNotValidPassengerRating = PassengerRatingTestUtil.getDefaultNotValidPassengerRating();
        RideResponse rideInfo = RideTestUtil.getDefaultRideResponse();

        when(rideService.getRideById(anyLong())).thenReturn(rideInfo);
        assertThrows(
                RateNotValidException.class,
                () -> passengerRatingService.createRating(newNotValidPassengerRating, DEFAULT_ID)
        );
    }

    @Test
    void createPassengerRating_shouldReturnServiceNotAvailableException() {
        PassengerRating newPassengerRating = PassengerRatingTestUtil.getDefaultPreCreatedPassengerRating();
        when(rideService.getRideById(anyLong())).thenThrow(ServiceUnAvailableException.class);
        assertThrows(
                ServiceUnAvailableException.class,
                () -> passengerRatingService.createRating(newPassengerRating, DEFAULT_ID)
        );
        verify(rideService).getRideById(DEFAULT_ID);
    }

    @Test
    void updatePassengerRating_shouldReturnPassengerRating() {
        PassengerRating passengerRating = PassengerRatingTestUtil.getDefaultPassengerRating();

        when(passengerRatingRepository.findById(anyLong())).thenReturn(Optional.of(passengerRating));
        when(passengerRatingRepository.save(passengerRating)).thenReturn(passengerRating);

        PassengerRating updatedRating = passengerRatingService.updateRating(passengerRating, DEFAULT_ID);

        assertEquals(passengerRating.getRate(), updatedRating.getRate());
        assertEquals(passengerRating.getComment(), updatedRating.getComment());
        verify(passengerRatingRepository).findById(DEFAULT_ID);
        verify(passengerRatingRepository).save(passengerRating);
    }

    @Test
    void updatePassengerRating_shouldReturnRatingNotFoundException() {
        PassengerRating passengerRating = PassengerRatingTestUtil.getDefaultPassengerRating();

        when(passengerRatingRepository.findById(anyLong())).thenThrow(RatingNotFoundException.class);

        assertThrows(
                RatingNotFoundException.class,
                () -> passengerRatingService.updateRating(passengerRating, DEFAULT_ID)
        );

        verify(passengerRatingRepository).findById(DEFAULT_ID);
    }

    @Test
    void updatePassengerRating_shouldReturnRateNotValidException() {
        PassengerRating passengerRating = PassengerRatingTestUtil.getDefaultPassengerRating();
        PassengerRating notValidPassengerRating = PassengerRatingTestUtil.getDefaultNotValidPassengerRating();

        when(passengerRatingRepository.findById(anyLong())).thenReturn(Optional.of(passengerRating));
        assertThrows(
                RateNotValidException.class,
                () -> passengerRatingService.updateRating(notValidPassengerRating, DEFAULT_ID)
        );
    }
}
