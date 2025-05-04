package com.firom.ecom_api.common.exceptions;

import com.firom.ecom_api.common.enums.ErrorCode;

public class ResourceNotFoundException extends CustomRuntimeException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

    public  ResourceNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    };
}
