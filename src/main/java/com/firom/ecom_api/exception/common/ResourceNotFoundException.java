package com.firom.ecom_api.exception.common;

import com.firom.ecom_api.exception.enums.ErrorCode;

public class ResourceNotFoundException extends CustomRuntimeException {

    public  ResourceNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    };
}
