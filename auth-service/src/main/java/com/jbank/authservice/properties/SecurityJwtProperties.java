package com.jbank.authservice.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@ConfigurationProperties(prefix = "security.jwt")
@Validated
@Data
public class SecurityJwtProperties {

    @NotBlank
    private String secretKey;
    @NotNull
    private Duration tokenExpiration;
    @NotNull
    private Duration refreshTokenExpiration;
}
