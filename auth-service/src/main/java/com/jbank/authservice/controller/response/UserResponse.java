package com.jbank.authservice.controller.response;

import java.time.LocalDate;

public record UserResponse(String firstName, String middleName, String lastName, String email, String phoneNumber,
                           LocalDate dateOfBirth) {
}
