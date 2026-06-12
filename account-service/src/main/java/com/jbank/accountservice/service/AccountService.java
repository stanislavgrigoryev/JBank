package com.jbank.accountservice.service;

import com.jbank.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final RestClient.Builder restClient;
    private final AccountRepository accountRepository;


    public String getById() {
       return restClient.build().get().uri("http://localhost:8086/api/v1/cards").retrieve().body(String.class);

    }
}
