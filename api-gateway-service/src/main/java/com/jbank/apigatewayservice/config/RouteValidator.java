package com.jbank.apigatewayservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteValidator {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final List<String> openEndpoints = List.of(
            "/api/v1/auth/signin",
            "/api/v1/user/register",
            "/api/v1/user/email/verification",
            "/api/v1/user/resend/verification-code",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/auth-docs/**",
            "/account-docs/**",
            "/eureka/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(pattern -> pathMatcher.match(pattern, request.getURI().getPath()));
}