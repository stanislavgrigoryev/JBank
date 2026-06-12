package com.jbank.authservice.dto;

public record PassportDto(String series, String number, String issued, String registrationAddress) {
}
