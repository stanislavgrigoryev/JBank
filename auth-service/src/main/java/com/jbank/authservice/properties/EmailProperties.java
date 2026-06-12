package com.jbank.authservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "spring.mail")
@Validated
@Data
public class EmailProperties {

    private String username;

    private String password;

    private String support;
}
