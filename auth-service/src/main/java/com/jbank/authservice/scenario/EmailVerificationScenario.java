package com.jbank.authservice.scenario;

import com.jbank.authservice.controller.request.UserEmailVerificationRequest;
import com.jbank.authservice.controller.response.UserResponse;
import com.jbank.authservice.entity.User;
import com.jbank.authservice.mapper.UserMapper;
import com.jbank.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationScenario {
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse emailVerification(UserEmailVerificationRequest userEmailVerificationRequest, Long userId) {
        log.info("Email verification for user with id {}", userId);
        User user = userService.findById(userId);
        userService.emailVerification(userEmailVerificationRequest.verificationCode(), user);
        return userMapper.toUserResponse(user);
    }
}
