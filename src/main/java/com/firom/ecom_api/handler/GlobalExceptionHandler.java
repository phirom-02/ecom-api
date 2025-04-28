package com.firom.ecom_api.handler;

import com.firom.ecom_api.common.dto.ApiError;
import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.common.exceptions.CustomRuntimeException;
import com.firom.ecom_api.common.exceptions.ResourceNotFoundException;
import com.firom.ecom_api.common.enums.ErrorCode;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericExceptions(Exception e) {
        var error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR,
                e.getStackTrace()
        );

        log.error(error.toString());

        return ApiResponseHandler.error(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericRuntimeExceptions(CustomRuntimeException e) {
        var error = new ApiError(
                HttpStatus.BAD_REQUEST,
                e.getErrorCode(),
                e.getStackTrace()
        );

        log.error(error.toString());

        return ApiResponseHandler.error(null, error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> descriptions = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        var error = new ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR,
                e.getStackTrace(),
                Strings.join(descriptions, '\n')
        );

        return ApiResponseHandler.error(null, error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        var error = new ApiError(
                HttpStatus.NOT_FOUND,
                e.getErrorCode(),
                e.getStackTrace()
        );

        return ApiResponseHandler.error(null, error, HttpStatus.NOT_FOUND);
    }
}
