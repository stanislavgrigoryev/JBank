package com.jbank.authservice.controller.response;

public record RefreshTokenResponse(String accessToken, String refreshToken) {
}
