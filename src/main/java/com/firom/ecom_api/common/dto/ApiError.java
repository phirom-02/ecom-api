package com.firom.ecom_api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.firom.ecom_api.common.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String errorCode;
    private String error;
    private String trace;
    private List<String> description;
    private String message;

    public  ApiError(
            HttpStatus httpStatus,
            ErrorCode errorCode,
            String message,
            StackTraceElement[] trace
    ) {
        this.error = httpStatus.getReasonPhrase();
        this.trace = Arrays.toString(trace);
        this.message = message;
        this.errorCode = errorCode.getCode();
        this.description = null;
    }

    public ApiError(
            HttpStatus httpStatus,
            ErrorCode errorCode,
            StackTraceElement[] trace,
            List<String> description
    ) {
        this.error = httpStatus.getReasonPhrase();
        this.trace = Arrays.toString(trace);
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
        this.description = description;
    }

    public ApiError(Object o, ApiError error) {
    }
}
