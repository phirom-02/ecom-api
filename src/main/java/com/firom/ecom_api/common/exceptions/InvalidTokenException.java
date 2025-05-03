package com.firom.ecom_api.common.exceptions;

import com.firom.ecom_api.common.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenException extends CustomRuntimeException {

    public InvalidTokenException() {
        super("Invalid token", ErrorCode.UNAUTHORIZED);
    }

    public InvalidTokenException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
