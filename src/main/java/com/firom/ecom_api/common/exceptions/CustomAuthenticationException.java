package com.firom.ecom_api.common.exceptions;

import com.firom.ecom_api.common.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class CustomAuthenticationException extends AuthenticationException {
    private ErrorCode errorCode;

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomAuthenticationException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
