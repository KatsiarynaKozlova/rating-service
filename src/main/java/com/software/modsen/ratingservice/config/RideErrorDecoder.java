package com.software.modsen.ratingservice.config;

import com.software.modsen.ratingservice.exception.NotFoundException;
import com.software.modsen.ratingservice.exception.ServiceUnAvailableException;
import com.software.modsen.ratingservice.util.ExceptionMessages;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class RideErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        if (status == HttpStatus.NOT_FOUND.value()) {
            return new NotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND_EXCEPTION);
        }
        if (status >= HttpStatus.INTERNAL_SERVER_ERROR.value() && status < 600) {
            return new ServiceUnAvailableException(ExceptionMessages.RIDE_SERVICE_IS_NOT_AVAILABLE);
        }
        return new RetryableException(
                response.status(),
                exception.getMessage(),
                response.request().httpMethod(),
                exception,
                (Long) null,
                response.request()
        );
    }
}