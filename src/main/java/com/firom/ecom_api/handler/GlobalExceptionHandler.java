package com.firom.ecom_api.handler;

import com.firom.ecom_api.exception.ApiError;
import com.firom.ecom_api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleExceptions(Exception e) {
        var error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                e.getStackTrace()
        );

        log.error(error.toString());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeExceptions(Exception e) {
        var error = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                e.getStackTrace()
        );

        log.error(error.toString());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(RuntimeException e) {
        var error = new ApiError(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                e.getStackTrace()
        );

        log.error(error.toString());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
