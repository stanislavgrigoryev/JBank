package com.jbank.authservice.controller.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
