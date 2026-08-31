package com.jbank.authservice.service;

import com.jbank.authservice.controller.request.RegisterUserRequest;
import com.jbank.authservice.controller.response.RefreshTokenResponse;
import com.jbank.authservice.entity.Passport;
import com.jbank.authservice.entity.RefreshToken;
import com.jbank.authservice.entity.RoleType;
import com.jbank.authservice.entity.User;
import com.jbank.authservice.exception.*;
import com.jbank.authservice.repository.UserRepository;
import com.jbank.authservice.security.jwt.JwtUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

import static com.jbank.authservice.service.EmailService.generateVerificationCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;


    public User get(Principal principal) {
        return getByEmail(principal.getName());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User with email {} not found: " + email));
    }

    public User save(RegisterUserRequest registerUserRequest) {

        if (userRepository.existsByEmailIgnoreCase(registerUserRequest.email())) {
            throw new AlreadyExistsException("Email already exists");
        }
        Passport passport = Passport.builder()
                .series(registerUserRequest.passport().series())
                .number(registerUserRequest.passport().number())
                .registrationAddress(registerUserRequest.passport().registrationAddress())
                .issued(registerUserRequest.passport().issued()).build();

        var user = User.builder()
                .firstName(registerUserRequest.firstName())
                .middleName(registerUserRequest.middleName())
                .lastName(registerUserRequest.lastName())
                .email(registerUserRequest.email())
                .phoneNumber(registerUserRequest.phoneNumber())
                .dateOfBirth(registerUserRequest.dateOfBirth())
                .password(passwordEncoder.encode(registerUserRequest.password()))
                .roles(Set.of(RoleType.CLIENT))
                .verificationCode(generateVerificationCode())
                .verificationCodeExpiresAt(LocalDateTime.now().plusMinutes(60))
                .emailVerification(false)
                .passport(passport)
                .build();

        sendVerificationEmail(user);
        userRepository.save(user);
        return user;
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            log.error("Couldn't send verification email to {}: {}", user.getEmail(), e.getMessage(), e);
        }
    }

    public void resendVerificationCode(User user) {
        log.info("Resend verification code for user with id {}:", user.getId());

        if (user.isEnabled()) {
            throw new UserAlreadyVerifiedException("User is enabled");
        }
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        sendVerificationEmail(user);
        userRepository.save(user);
    }

    public void emailVerification(String verificationCode, User user) {
        log.info("Email verification for user with id {}:", user.getId());

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new VerificationCodeExpiredException("Verification code expired");
        }

        if (user.getVerificationCode().equals(verificationCode)) {
            user.setEmailVerification(true);
            user.setVerificationCode(null);
            user.setVerificationCodeExpiresAt(null);
            userRepository.save(user);
        } else {
            throw new InvalidVerificationCodeException("Invalid verification code");
        }
    }

    @Transactional
    public RefreshTokenResponse refreshToken(String refreshTokenRequest) {
        log.info("Refresh token");
        return refreshTokenService.findByRefreshToken(refreshTokenRequest)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    User tokenOwner = userRepository.findById(userId).orElseThrow(() -> new RefreshTokenException("Exception trying to get token for userId " + userId));
                    String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUsername());

                    return new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException(refreshTokenRequest, "refresh token not found"));
    }

    @Transactional
    public void logout() {
//        ResponseEntity.ok(new SimpleResponse("User logged out successfully. Username is : " + userDetails.getUsername()));
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof User user) {
            Long userId = user.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }

    @Transactional
    public void changePassword(Long userId, String newPassword) {
        log.info("Change password for user {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        refreshTokenService.deleteByUserId(user.getId());
    }
}
