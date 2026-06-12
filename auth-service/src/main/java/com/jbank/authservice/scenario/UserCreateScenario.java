package com.jbank.authservice.scenario;

import com.jbank.authservice.controller.request.RegisterUserRequest;
import com.jbank.authservice.controller.response.UserResponse;
import com.jbank.authservice.entity.User;
import com.jbank.authservice.mapper.UserMapper;
import com.jbank.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreateScenario {

    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse register(RegisterUserRequest registerUserRequest) {
        log.info("Registering user");
        User user = userService.save(registerUserRequest);
        return userMapper.toUserResponse(user);
    }
}
