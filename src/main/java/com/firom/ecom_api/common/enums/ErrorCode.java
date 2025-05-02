package com.firom.ecom_api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // General error codes: 1000 - 1999
    INTERNAL_SERVER_ERROR("1000", "Internal Server Error"),
    BAD_REQUEST("1001", "Bad Request"),
    FORBIDDEN("1002", "Forbidden"),
    UNAUTHORIZED("1003", "Unauthorized"),
    VALIDATION_ERROR("1004", "Validation Error"),

    // Auth error codes: 2000 - 2999
    INVALID_TOKEN("2000", "Invalid Token"),

    // Product error codes: 3000 - 3999
    PRODUCT_NOT_FOUND("3000", "Product not found");


    private final String code;
    private final String message;
}
