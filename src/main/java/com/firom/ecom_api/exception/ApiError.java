package com.firom.ecom_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.firom.ecom_api.exception.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Arrays;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String errorCode;
    private String error;
    private String message;
    private String trace;
    private String description;
    private Instant timestamp;

    public  ApiError() {}

    public  ApiError(
            HttpStatus httpStatus,
            ErrorCode errorCode,
            StackTraceElement[] trace
    ) {
        this.error = httpStatus.getReasonPhrase();
        this.trace = Arrays.toString(trace);
        this.timestamp = Instant.now();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
        this.description = null;
    }

    public ApiError(
            HttpStatus httpStatus,
            ErrorCode errorCode,
            StackTraceElement[] trace,
            String description
    ) {
        this.error = httpStatus.getReasonPhrase();
        this.trace = Arrays.toString(trace);
        this.timestamp = Instant.now();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
        this.description = description;
    }
}
