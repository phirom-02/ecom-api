package com.firom.ecom_api.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class Meta extends HashMap<String, Object> {
    private Instant timestamp;
    private String message;

    public Meta(String message) {
        this.timestamp = Instant.now();
        this.message = message;
    }
}