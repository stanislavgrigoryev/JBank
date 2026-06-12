package com.jbank.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeDto(@NotBlank @Size(min = 8, max = 20) String password) {
}
