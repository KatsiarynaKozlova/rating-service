package com.software.modsen.ratingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RateNotValidException extends RuntimeException {
    RateNotValidException(String s) {
        super(s);
    }
}
