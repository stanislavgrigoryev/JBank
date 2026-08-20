package com.jbank.accountservice.dto;

import com.jbank.accountservice.entity.Currency;

public record UpdateAccountRequest(
        Currency currency) {
}
