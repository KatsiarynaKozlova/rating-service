package com.software.modsen.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.software.modsen.ratingservice.config.DatabaseContainerConfiguration;
import com.software.modsen.ratingservice.config.KafkaContainerConfiguration;
import com.software.modsen.ratingservice.dto.request.RatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.RatingResponse;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.util.DriverRatingTestUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.software.modsen.ratingservice.util.ApiConstants.*;
import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.DEFAULT_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({DatabaseContainerConfiguration.class,
        KafkaContainerConfiguration.class})
@AutoConfigureWireMock(port = RIDE_WIRE_MOCK_PORT)
public class DriverControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void testGetByIdRating_ShouldReturnNotFoundException() throws Exception {
        mockMvc.perform(get(GET_DRIVER_RATING_BY_RATING_ID_URL, DEFAULT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    public void testCreateDriverRating_ShouldReturnNewRating() throws Exception {
        RatingRequest newRatingRequest = DriverRatingTestUtil.getDefaultRatingRequest();
        DriverRating expectedResponse = DriverRatingTestUtil.getDefaultDriverRating();

        stubFor(WireMock.get(urlEqualTo(GET_RIDE_WIRE_MOCK_URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                    {
                                                           "id": 1,
                                                           "driverId": 1,
                                                           "passengerId": 1,
                                                           "routeStart": "Point A",
                                                           "routeEnd": "Point B",
                                                           "price": 123.45,
                                                           "dateTimeCreate": [2024,9,1,5,51,26],
                                                           "status": "ACCEPTED"
                                                         }
                                """)));

        mockMvc.perform(post(POST_DRIVER_RATING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRatingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.passengerId").value(expectedResponse.getPassengerId()))
                .andExpect(jsonPath("$.driverId").value(expectedResponse.getDriverId()))
                .andExpect(jsonPath("$.rate").value(expectedResponse.getRate()))
                .andExpect(jsonPath("$.comment").value(expectedResponse.getComment()));
    }

    @Test
    @Order(3)
    public void testGetByIdRating_ShouldReturnRating() throws Exception {
        RatingResponse expectedResponse = DriverRatingTestUtil.getDefaultRatingResponse();
        mockMvc.perform(get(GET_DRIVER_RATING_BY_RATING_ID_URL, DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverId").value(expectedResponse.getDriverId()))
                .andExpect(jsonPath("$.passengerId").value(expectedResponse.getPassengerId()))
                .andExpect(jsonPath("$.rate").value(expectedResponse.getRate()))
                .andExpect(jsonPath("$.comment").value(expectedResponse.getComment()));
    }

    @Test
    @Order(4)
    public void testGetAverageDriverRating_ShouldReturnDriverRating() throws Exception {
        DriverRatingResponse expectedRating = DriverRatingTestUtil.getDefaultDriverRatingResponse();
        mockMvc.perform(get(GET_DRIVER_RATING_BY_DRIVER_ID_URL, DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverId").value(expectedRating.getDriverId()))
                .andExpect(jsonPath("$.rate").value(expectedRating.getRate()));
    }

    @Test
    @Order(5)
    public void testUpdateDriverRating_ShouldReturnDriverRating() throws Exception {
        RatingRequest updatedRating = DriverRatingTestUtil.getDefaultRatingRequest();
        RatingResponse expectedResponse = DriverRatingTestUtil.getDefaultRatingResponse();
        mockMvc.perform(put(PUT_DRIVER_RATING_URL, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRating)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.passengerId").value(expectedResponse.getPassengerId()))
                .andExpect(jsonPath("$.driverId").value(expectedResponse.getDriverId()))
                .andExpect(jsonPath("$.rate").value(expectedResponse.getRate()))
                .andExpect(jsonPath("$.comment").value(expectedResponse.getComment()));
    }

    @Test
    public void testDeleteDriverRating_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(DELETE_DRIVER_RATING_URL, DEFAULT_ID))
                .andExpect(status().isNoContent());
    }
}
