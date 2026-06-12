package com.jbank.accountservice.service;

import com.jbank.accountservice.dto.AccountDto;
import com.jbank.accountservice.dto.CreateAccountRequest;
import com.jbank.accountservice.dto.UpdateAccountRequest;
import com.jbank.accountservice.entity.Account;
import com.jbank.accountservice.entity.AccountStatus;
import com.jbank.accountservice.entity.AccountType;
import com.jbank.accountservice.exception.AccessForbiddenException;
import com.jbank.accountservice.exception.AccountNotFoundException;
import com.jbank.accountservice.mapper.AccountMapper;
import com.jbank.accountservice.repository.AccountRepository;
import com.jbank.accountservice.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountDto createAccount(Long userId, CreateAccountRequest request) {
        String accountNumber = generateUniqueAccountNumber();

        Account account = Account.builder()
                .userId(userId)
                .typeAccount(request.typeAccount())
                .currency(request.currency())
                .accountNumber(accountNumber)
                .availableBalance(BigDecimal.ZERO)
                .blockedBalance(BigDecimal.ZERO)
                .accountStatus(AccountStatus.ACTIVE)
                .interestRate(resolveInterestRate(request.typeAccount()))
                .creditLimit(resolveCreditLimit(request.typeAccount()))
                .dateOpenAccount(LocalDate.now())
                .build();

        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public List<AccountDto> getMyAccounts(Long userId) {
        return accountMapper.toDtoList(accountRepository.findAllByUserId(userId));
    }

    @Transactional(readOnly = true)
    public AccountDto getAccount(Long accountId, Long userId) {
        Account account = findOwnedAccount(accountId, userId);
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto updateAccount(Long accountId, Long userId, UpdateAccountRequest request) {
        Account account = findOwnedAccount(accountId, userId);

        if (request.currency() != null) {
            account.setCurrency(request.currency());
        }

        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional
    public void closeAccount(Long accountId, Long userId) {
        Account account = findOwnedAccount(accountId, userId);
        account.setAccountStatus(AccountStatus.CLOSED);
        account.setDateCloseAccount(LocalDate.now());
        accountRepository.save(account);
    }

    private Account findOwnedAccount(Long accountId, Long userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        if (!account.getUserId().equals(userId)) {
            throw new AccessForbiddenException();
        }

        return account;
    }

    private String generateUniqueAccountNumber() {
        String number;
        do {
            number = AccountNumberGenerator.generate();
        } while (accountRepository.existsByAccountNumber(number));
        return number;
    }

    private BigDecimal resolveInterestRate(AccountType type) {
        return type == AccountType.SAVINGS ? new BigDecimal("5.00") : null;
    }

    private BigDecimal resolveCreditLimit(AccountType type) {
        return type == AccountType.CREDIT ? new BigDecimal("50000.00") : null;
    }
}
