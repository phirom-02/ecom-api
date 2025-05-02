package com.firom.ecom_api.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestPropertiesUtils {

    public static String extractBearerToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
