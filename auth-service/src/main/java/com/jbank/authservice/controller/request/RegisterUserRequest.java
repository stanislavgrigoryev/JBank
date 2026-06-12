package com.jbank.authservice.controller.request;

import com.jbank.authservice.dto.PassportDto;
import com.jbank.authservice.entity.RoleType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record RegisterUserRequest(
        @NotBlank
        @Size(min = 1, max = 255)
        String firstName,
        @NotBlank
        @Size(min = 1, max = 255)
        String middleName,
        @Size(min = 1, max = 255)
        String lastName,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 1, max = 255)
        String phoneNumber,
        @NotEmpty
        Set<RoleType> roles,
        @NotBlank
        @Size(min = 6, max = 20)
        String password,
        @NotNull
        LocalDate dateOfBirth,
        PassportDto passport) {
}
