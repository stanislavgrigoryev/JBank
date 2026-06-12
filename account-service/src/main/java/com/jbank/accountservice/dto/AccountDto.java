package com.jbank.accountservice.dto;

import com.jbank.accountservice.entity.AccountStatus;
import com.jbank.accountservice.entity.AccountType;
import com.jbank.accountservice.entity.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDto(
        Long accountId,
        AccountType typeAccount,
        Currency currency,
        String accountNumber,
        BigDecimal availableBalance,
        BigDecimal blockedBalance,
        AccountStatus accountStatus,
        BigDecimal interestRate,
        BigDecimal creditLimit,
        LocalDate dateOpenAccount,
        LocalDate dateCloseAccount
) {
}
