package com.jbank.accountservice.dto;

import com.jbank.accountservice.entity.AccountType;
import com.jbank.accountservice.entity.Currency;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull AccountType typeAccount,
        @NotNull Currency currency) {
}
