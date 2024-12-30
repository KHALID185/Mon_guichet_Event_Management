package org.gatewaymicroservice.filter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.gatewaymicroservice.routerFilter.RouterValidator;
import org.gatewaymicroservice.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;
    private final RouterValidator routerValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routerValidator.isSecured.test(request)) {
            if (authMissing(request)) return onError(exchange);
            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
            try {
                jwtUtil.validateToken(token);

            } catch (Exception e) {
                return onError(exchange);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}