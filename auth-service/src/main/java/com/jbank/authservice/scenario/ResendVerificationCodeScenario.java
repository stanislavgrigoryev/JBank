package com.jbank.authservice.scenario;

import com.jbank.authservice.entity.User;
import com.jbank.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResendVerificationCodeScenario {
    private final UserService userService;

    @Transactional
    public void resendVerificationCode(Long userId) {
        log.info("Resend verification code for user {}", userId);
        User user = userService.findById(userId);
        userService.resendVerificationCode(user);
    }
}
