package com.firom.ecom_api.common.enums;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS("ACCESS"),
    REQUEST("REFRESH");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

}
