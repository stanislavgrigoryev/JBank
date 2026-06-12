package com.jbank.accountservice.controller;

import com.jbank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping()
    public String name(){
       return accountService.getById();
    }
}
