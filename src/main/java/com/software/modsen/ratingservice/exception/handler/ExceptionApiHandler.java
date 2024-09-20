package com.software.modsen.ratingservice.exception.handler;

import com.software.modsen.ratingservice.exception.RatingNotFoundException;
import com.software.modsen.ratingservice.exception.RideNotFoundException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler({RideNotFoundException.class, RatingNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));

    }
}
