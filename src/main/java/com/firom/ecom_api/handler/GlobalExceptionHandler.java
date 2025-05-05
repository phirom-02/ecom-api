package com.firom.ecom_api.handler;

import com.firom.ecom_api.common.dto.ApiError;
import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.common.exceptions.CustomAuthenticationException;
import com.firom.ecom_api.common.exceptions.CustomRuntimeException;
import com.firom.ecom_api.common.exceptions.InvalidTokenException;
import com.firom.ecom_api.common.exceptions.ResourceNotFoundException;
import com.firom.ecom_api.common.enums.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// TODO: Handle authentication exceptions
// TODO: Handle {IllegalArgumentException} exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericRuntimeExceptions(final CustomRuntimeException e) {
        var error = new ApiError(
                HttpStatus.BAD_REQUEST,
                e.getErrorCode(),
                e.getMessage(),
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
                descriptions
        );

        log.error(error.toString());

        return ApiResponseHandler.error(null, error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        var error = new ApiError(
                HttpStatus.NOT_FOUND,
                e.getErrorCode(),
                e.getMessage(),
                e.getStackTrace()
        );

        log.error(error.toString());

        return ApiResponseHandler.error(null, error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidTokenException(InvalidTokenException e) {
        var error = new ApiError(
                HttpStatus.UNAUTHORIZED,
                e.getErrorCode(),
                e.getMessage(),
                e.getStackTrace()
        );

        return ApiResponseHandler.error(null, error, HttpStatus.UNAUTHORIZED);
    }

    // ------------------------------------- JWT -------------------------------------
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtException(JwtException e) {
        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.INVALID_TOKEN,
                e.getMessage(),
                e.getStackTrace()
        );

        if(e instanceof ExpiredJwtException) {
            ErrorCode errorCode = ErrorCode.EXPIRED_TOKEN;
            error.setMessage(errorCode.getMessage());
            error.setErrorCode(errorCode.getCode());
            error.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return ApiResponseHandler.error(null, error, HttpStatus.UNAUTHORIZED);
    }

    // ------------------------------------- Authentication -------------------------------------
    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(CustomAuthenticationException e) {
        var error = new ApiError(
                HttpStatus.UNAUTHORIZED,
                e.getErrorCode(),
                e.getMessage(),
                e.getStackTrace()
        );

        log.error(error.toString());

        return ApiResponseHandler.error(null, error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericExceptions(Exception e) {
        var error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                e.getStackTrace()
        );

        log.error(error.toString());

        return ApiResponseHandler.error(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
