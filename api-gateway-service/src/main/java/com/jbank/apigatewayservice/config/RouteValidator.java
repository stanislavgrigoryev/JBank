package com.jbank.apigatewayservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteValidator {

    public final static List<String> openEndpoints =
            List.of("/auth/signin",
                    "/eureka",
                    "/user/register",
                    "/api-docs");

    public Predicate<ServerHttpRequest> isSecured =
            request ->
                    openEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
