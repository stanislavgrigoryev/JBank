package com.jbank.accountservice.controller;

import com.jbank.accountservice.dto.AccountDto;
import com.jbank.accountservice.dto.CreateAccountRequest;
import com.jbank.accountservice.dto.UpdateAccountRequest;
import com.jbank.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestHeader("X-User-Id") Long userId,
                                    @Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getMyAccounts(@RequestHeader("X-User-Id") Long userId) {
        return accountService.getMyAccounts(userId);
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(@RequestHeader("X-User-Id") Long userId,
                                 @PathVariable Long accountId) {
        return accountService.getAccount(accountId, userId);
    }

    @PatchMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateAccount(@RequestHeader("X-User-Id") Long userId,
                                    @PathVariable Long accountId,
                                    @RequestBody UpdateAccountRequest request) {
        return accountService.updateAccount(accountId, userId, request);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeAccount(@RequestHeader("X-User-Id") Long userId,
                             @PathVariable Long accountId) {
        accountService.closeAccount(accountId, userId);
    }
}
