package com.jbank.authservice.controller.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthenticationResponse(Long id, String token, String refreshToken, String username, List<String> roles) {
}
