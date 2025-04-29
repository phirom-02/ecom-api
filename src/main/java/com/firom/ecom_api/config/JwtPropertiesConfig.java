package com.firom.ecom_api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration()
@ConfigurationProperties(prefix = "jwt")
public class JwtPropertiesConfig {

    private String secretKey;
    private String expirationTime;
}