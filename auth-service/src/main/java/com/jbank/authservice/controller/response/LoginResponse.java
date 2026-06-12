package com.jbank.authservice.controller.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String expiresIn;
}
