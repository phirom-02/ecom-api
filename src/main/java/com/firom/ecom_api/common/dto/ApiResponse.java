package com.firom.ecom_api.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private Meta meta;
    private T data;

    public ApiResponse(T data, String message) {
        var meta = new Meta();
        meta.put("message", message);
        meta.put("timestamp", Instant.now());

        this.meta = meta;
        this.data = data;
    }

    public ApiResponse(T data, ApiError error) {
        var meta = new Meta();
        meta.put("error", error.getError());
        meta.put("trace", error.getTrace());
        meta.put("message", error.getMessage());
        meta.put("errorCode", error.getErrorCode());
        meta.put("description", error.getDescription());
        meta.put("timestamp", Instant.now());

        this.meta = meta;
        this.data = data;
    }
}

