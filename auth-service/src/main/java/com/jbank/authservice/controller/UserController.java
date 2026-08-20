package com.jbank.authservice.controller;

import com.jbank.authservice.controller.request.RefreshTokenRequest;
import com.jbank.authservice.controller.request.RegisterUserRequest;
import com.jbank.authservice.controller.request.UserEmailVerificationRequest;
import com.jbank.authservice.controller.response.RefreshTokenResponse;
import com.jbank.authservice.controller.response.SimpleResponse;
import com.jbank.authservice.controller.response.UserResponse;
import com.jbank.authservice.dto.PasswordChangeDto;
import com.jbank.authservice.scenario.EmailVerificationScenario;
import com.jbank.authservice.scenario.ResendVerificationCodeScenario;
import com.jbank.authservice.scenario.UserCreateScenario;
import com.jbank.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;
    private final EmailVerificationScenario emailVerificationScenario;
    private final ResendVerificationCodeScenario resendVerificationCodeScenario;
    private final UserCreateScenario userCreateScenario;

    @Operation(summary = "Регистрация пользователя.",
            description = "Метод позволяет создать нового пользователя, предоставив необходимые данные в теле запроса." +
                    "После успешной регистрации метод возвращает 'UserResponse' который содержит информацию о пользователе.")
    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return userCreateScenario.register(registerUserRequest);
    }

    @Operation(summary = "Обновление JWT токенов",
            description = "Метод позволяет обновить accessToken и refreshToken для пользователя, " +
                    "принимая в теле запроса валидный refreshToken проверяя его существование и срок действия. Возвращает " +
                    "'RefreshTokenResponse' который содержит токены с обновленным сроком действия.")
    @PostMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest.refreshToken());
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<SimpleResponse> logout(@AuthenticationPrincipal UserDetails userDetails) {
        userService.logout();
        return ResponseEntity.ok(new SimpleResponse("User logged out successfully. Username is : " + userDetails.getUsername()));

    }

    @Operation(summary = "Верификация email пользователя",
            description = "Этот метод позволяет верифицировать email пользователя. " +
                    "Пользователь отправляет код, который должен совпадать с кодом в БД. " +
                    "Если успешно, флаг будет emailVerification = true. Срок действия кода 5 минут",
            security = @SecurityRequirement(name = "bearer"))
    @PostMapping(path = "/email/verification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserResponse emailVerification(@RequestBody @Valid UserEmailVerificationRequest userEmailVerificationRequest, Principal principal) {
        return emailVerificationScenario.emailVerification(userEmailVerificationRequest, userService.get(principal).getId());
    }

    @Operation(summary = "Повторная отправка кода верификации",
            description = "Метод позволяет повторно отправить код на почту генерируя новый для верификации email. " +
                    "Старый код становится не действительным. Метод не возвращает содержимого, но возвращает HTTP статус 204 (No Content) при успешном отправлении.",
            security = @SecurityRequirement(name = "bearer"))
    @PostMapping(path = "/resend/verification-code")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resendVerificationCode(@Valid Principal principal) {
        resendVerificationCodeScenario.resendVerificationCode(userService.get(principal).getId());
    }

    @Operation(summary = "Смена пароля", description = "Метод позволяет поменять существующий пароль. " +
            "Старый пароль становится не действительным. Метод не возвращает содержимого, но возвращает HTTP статус 204 (No Content) при успешном отправлении.",
            security = @SecurityRequirement(name = "bearer"))
    @PatchMapping(path = "/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid PasswordChangeDto passwordChange, Principal principal) {
        userService.changePassword(userService.get(principal).getId(), passwordChange.password());
    }
}
