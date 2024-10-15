package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.response.RideResponse;
import com.software.modsen.ratingservice.exception.RateNotValidException;
import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.exception.RideNotFoundException;
import com.software.modsen.ratingservice.exception.ServiceUnAvailableException;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.impl.DriverRatingServiceImpl;
import com.software.modsen.ratingservice.util.DriverRatingTestUtil;
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
public class DriverRatingServiceUnitTest {
    @Mock
    private DriverRatingRepository driverRatingRepository;
    @Mock
    private RideService rideService;
    @InjectMocks
    private DriverRatingServiceImpl driverRatingService;

    @Test
    void getListOfDriversRatings_shouldReturnList() {
        when(driverRatingRepository.findAll()).thenReturn(Collections.emptyList());

        List<DriverRating> driverRatingList = driverRatingService.getAllRatings();

        assertNotNull(driverRatingList);
        assertEquals(0, driverRatingList.size());
        assertTrue(driverRatingList.isEmpty());
        verify(driverRatingRepository, times(1)).findAll();
    }

    @Test
    void getDriverRatingById_shouldReturnRating() {
        DriverRating expectedRating = DriverRatingTestUtil.getDeaultDriverRating();
        when(driverRatingRepository.findById(anyLong())).thenReturn(Optional.of(expectedRating));

        DriverRating resultDriverRating = driverRatingService.getRatingById(DEFAULT_ID);

        assertNotNull(resultDriverRating);
        assertEquals(expectedRating, resultDriverRating);
        assertEquals(DEFAULT_ID, resultDriverRating.getDriverId());
        verify(driverRatingRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void getDriverRatingById_shouldThrowNotFoundException() {
        assertThrows(RatingNotFoundException.class, () -> driverRatingService.getRatingById(DEFAULT_ID));
        verify(driverRatingRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void getAverageDriverRatingById_shouldReturnDouble() {
        double expectedRate = DEFAULT_RATE;
        when(driverRatingRepository.findAverageDriverRatingByDriverId(anyLong())).thenReturn(expectedRate);

        Double resultRate = driverRatingService.getAverageRatingById(DEFAULT_ID);

        assertNotNull(resultRate);
        assertEquals(expectedRate, resultRate);
        verify(driverRatingRepository, times(1)).findAverageDriverRatingByDriverId(DEFAULT_ID);
    }

    @Test
    void createDriverRating_shouldReturnDriverRating() {
        DriverRating newDriverRating = DriverRatingTestUtil.getDefaultPreCreatedDriverRating();
        DriverRating expectedCreatedDriverRating = DriverRatingTestUtil.getDeaultDriverRating();
        RideResponse rideInfo = RideTestUtil.getDefaultRideResponse();

        when(driverRatingRepository.save(any(DriverRating.class))).thenReturn(expectedCreatedDriverRating);
        when(rideService.getRideById(anyLong())).thenReturn(rideInfo);

        DriverRating resultNewDriverRating = driverRatingService.createRating(newDriverRating, DEFAULT_ID);

        assertNotNull(resultNewDriverRating);
        assertEquals(resultNewDriverRating, expectedCreatedDriverRating);
        verify(rideService, times(1)).getRideById(DEFAULT_ID);
        verify(driverRatingRepository, times(1)).save(newDriverRating);
    }

    @Test
    void createDriverRating_shouldReturnRideNotFoundException() {
        DriverRating newDriverRating = DriverRatingTestUtil.getDefaultPreCreatedDriverRating();

        when(rideService.getRideById(anyLong())).thenThrow(RideNotFoundException.class);
        assertThrows(
                RideNotFoundException.class,
                () -> driverRatingService.createRating(newDriverRating, DEFAULT_ID)
        );
        verify(rideService).getRideById(DEFAULT_ID);
    }

    @Test
    void createDriverRating_shouldReturnRateNotValidException() {
        DriverRating newNotValidDriverRating = DriverRatingTestUtil.getDefaultNotValidDriverRating();
        RideResponse rideInfo = RideTestUtil.getDefaultRideResponse();

        when(rideService.getRideById(anyLong())).thenReturn(rideInfo);
        assertThrows(
                RateNotValidException.class,
                () -> driverRatingService.createRating(newNotValidDriverRating, DEFAULT_ID)
        );
    }

    @Test
    void createDriverRating_shouldReturnServiceNotAvailableException() {
        DriverRating newDriverRating = DriverRatingTestUtil.getDefaultPreCreatedDriverRating();
        when(rideService.getRideById(anyLong())).thenThrow(ServiceUnAvailableException.class);
        assertThrows(
                ServiceUnAvailableException.class,
                () -> driverRatingService.createRating(newDriverRating, DEFAULT_ID)
        );
        verify(rideService).getRideById(DEFAULT_ID);
    }

    @Test
    void updateDriverRating_shouldReturnDriverRating() {
        DriverRating driverRating = DriverRatingTestUtil.getDeaultDriverRating();

        when(driverRatingRepository.findById(anyLong())).thenReturn(Optional.of(driverRating));
        when(driverRatingRepository.save(driverRating)).thenReturn(driverRating);

        DriverRating updatedRating = driverRatingService.updateRating(driverRating, DEFAULT_ID);

        assertEquals(driverRating.getRate(), updatedRating.getRate());
        assertEquals(driverRating.getComment(), updatedRating.getComment());
        verify(driverRatingRepository).findById(DEFAULT_ID);
        verify(driverRatingRepository).save(driverRating);
    }

    @Test
    void updateDriverRating_shouldReturnRatingNotFoundException() {
        DriverRating driverRating = DriverRatingTestUtil.getDeaultDriverRating();

        when(driverRatingRepository.findById(anyLong())).thenThrow(RatingNotFoundException.class);

        assertThrows(
                RatingNotFoundException.class,
                () -> driverRatingService.updateRating(driverRating, DEFAULT_ID)
        );

        verify(driverRatingRepository).findById(DEFAULT_ID);
    }

    @Test
    void updateDriverRating_shouldReturnRateNotValidException() {
        DriverRating driverRating = DriverRatingTestUtil.getDeaultDriverRating();
        DriverRating notValidDriverRating = DriverRatingTestUtil.getDefaultNotValidDriverRating();

        when(driverRatingRepository.findById(anyLong())).thenReturn(Optional.of(driverRating));
        assertThrows(
                RateNotValidException.class,
                () -> driverRatingService.updateRating(notValidDriverRating, DEFAULT_ID)
        );
    }
}
