package com.firom.ecom_api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // General error codes: 1000 - 1999
    INTERNAL_SERVER_ERROR("1000", "Internal server error"),
    BAD_REQUEST("1001", "Bad request"),
    FORBIDDEN("1002", "Forbidden"),
    UNAUTHORIZED("1003", "Unauthorized"),
    VALIDATION_ERROR("1004", "Validation error"),

    // Auth and Account error codes: 2000 - 2999
    INVALID_TOKEN("2000", "Invalid token"),
    EXPIRED_TOKEN("2001", "Token expired"),
    ACCOUNT_NOT_VERIFIED("2002", "Account is not verified"),

    // User error codes: 3000 - 3999
    USER_NOT_FOUND("3000", "User not found"),

    // Product error codes: 4000 - 4999
    PRODUCT_NOT_FOUND("4000", "Product not found");

    private final String code;
    private final String message;
}
