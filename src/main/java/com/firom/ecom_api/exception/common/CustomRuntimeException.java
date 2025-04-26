package com.firom.ecom_api.exception.common;

import com.firom.ecom_api.exception.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomRuntimeException extends RuntimeException {
    ErrorCode errorCode;

    public CustomRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
