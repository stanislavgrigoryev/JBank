package com.jbank.apigatewayservice.security;

import com.jbank.apigatewayservice.config.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AdminRoleGatewayFilter extends AbstractGatewayFilterFactory<AdminRoleGatewayFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtils jwtUtils;

    public AdminRoleGatewayFilter() {
        super(AdminRoleGatewayFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(AdminRoleGatewayFilter.Config config) {
        return (exchange, chain) -> {
            if (!validator.isSecured.test(exchange.getRequest())) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange, "Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);
            String role = jwtUtils.getUserRole(token);

            if (!(role.equals(RoleType.ADMIN.name()) || role.equals(RoleType.OPERATOR.name()))) {
                return forbidden(exchange, "You don't have rights to this resource");
            }

            try {
                jwtUtils.validate(token);
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                        .header("X-User-Roles", role)
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            } catch (Exception e) {
                return unauthorized(exchange, "Invalid or expired JWT token");
            }
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("X-Error-Message", message);
        return exchange.getResponse().setComplete();
    }
    private Mono<Void> forbidden(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().add("X-Error-Message", message);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}