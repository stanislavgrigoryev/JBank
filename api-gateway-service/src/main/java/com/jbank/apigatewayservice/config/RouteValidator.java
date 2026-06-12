package com.jbank.apigatewayservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteValidator {

    public final static List<String> openEndpoints =
            List.of(
                    "/api/v1/auth/signin",
                    "/api/v1/user/register",
                    "/api/v1/user/email/verification",
                    "/api/v1/user/resend/verification-code",
                    "/v3/api-docs",
                    "/swagger-ui",
                    "/auth-docs",
                    "/account-docs",
                    "/eureka"
            );

    public Predicate<ServerHttpRequest> isSecured =
            request ->
                    openEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
