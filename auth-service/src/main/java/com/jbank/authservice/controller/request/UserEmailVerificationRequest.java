package com.jbank.authservice.controller.request;

import jakarta.validation.constraints.NotBlank;

public record UserEmailVerificationRequest(@NotBlank String verificationCode) {
}
