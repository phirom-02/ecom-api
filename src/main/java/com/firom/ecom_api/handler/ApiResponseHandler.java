package com.firom.ecom_api.handler;

import com.firom.ecom_api.common.dto.ApiError;
import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.common.dto.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseHandler {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return new ResponseEntity<>(new ApiResponse<>(data, new Meta(HttpStatus.OK.getReasonPhrase())), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return new ResponseEntity<>(new ApiResponse<>(data, new Meta(message)), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return new ResponseEntity<>(new ApiResponse<>(data, new Meta(HttpStatus.CREATED.getReasonPhrase())), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return new ResponseEntity<>(new ApiResponse<>(data, new Meta(message)), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(T data, ApiError error, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse<>(data, error), status);
    }
}
