package com.jbank.authservice.security;

import com.jbank.authservice.controller.request.AuthenticationRequest;
import com.jbank.authservice.controller.response.AuthenticationResponse;
import com.jbank.authservice.entity.RefreshToken;
import com.jbank.authservice.entity.User;
import com.jbank.authservice.security.jwt.JwtUtils;
import com.jbank.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.email(), authenticationRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userPrincipal = (User) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userPrincipal.getId());
        return AuthenticationResponse.builder()
                .id(userPrincipal.getId())
                .token(jwtUtils.generateJwtToken(userPrincipal))
                .refreshToken(refreshToken.getToken())
                .username(userPrincipal.getUsername())
                .roles(roles)
                .build();
    }
}
