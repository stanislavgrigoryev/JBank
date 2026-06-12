package com.jbank.authservice.controller;

import com.jbank.authservice.controller.request.AuthenticationRequest;
import com.jbank.authservice.controller.response.AuthenticationResponse;
import com.jbank.authservice.security.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    private final SecurityService securityService;

    @Operation(summary = "Аутентификация пользователя",
            description = "Метод позволяет аутентифицировать пользователя в " +
                    "приложении создает для него token и refreshToken. Возвращает 'AuthenticationResponse' который содержит всю информацию.")
    @PostMapping(path = "/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return securityService.authenticate(authenticationRequest);
    }
}
