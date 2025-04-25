package com.firom.ecom_api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Arrays;

@Setter
@Getter
public class ApiError {
    private String errorCode;
    private String error;
    private String message;
    private String trace;
    private Instant timestamp;

//    Todo: Add custom errorCode
    public ApiError(HttpStatus httpStatus, String message, StackTraceElement[] trace) {
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.trace = Arrays.toString(trace);
        this.timestamp = Instant.now();
        this.errorCode = "1000";
    }
}
