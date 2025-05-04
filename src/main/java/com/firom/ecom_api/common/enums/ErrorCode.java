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

    // Auth and Account error codes: 2000 - 2999
    INVALID_TOKEN("2000", "Invalid Token"),
    ACCOUNT_NOT_VERIFIED("2001", "Account Not Verified"),

    // User error codes: 3000 - 3999
    USER_NOT_FOUND("3000", "User Not Found"),
    USER_NOT_VERIFIED("3001", "User Not Verified"),

    // Product error codes: 4000 - 4999
    PRODUCT_NOT_FOUND("4000", "Product not found");


    private final String code;
    private final String message;
}
