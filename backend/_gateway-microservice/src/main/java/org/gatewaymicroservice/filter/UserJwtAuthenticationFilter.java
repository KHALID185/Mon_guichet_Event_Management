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
public class UserJwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;
    private final RouterValidator routerValidator;

    /*@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routerValidator.isSecured.test(request)) {
            if (authMissing(request)) return onError(exchange);
            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
            try {
                jwtUtil.validateToken(token);
                Claims claims =jwtUtil.getClaims(token);
                if(!isUser(claims)){
                    return onError(exchange);
                }
            } catch (Exception e) {
                return onError(exchange);
            }
        }
        return chain.filter(exchange);
    }*/

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (authMissing(request)) return onError(exchange);

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove "Bearer " prefix

                try {
                    jwtUtil.validateToken(token); // Validates the token
                    Claims claims = jwtUtil.getClaims(token); // Extracts the claims from the token

                    if (isUser(claims)) {
                        // If the role is USER, add a custom header with the role information
                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                .header("X-User-Role", "USER")
                                .build();
                        // Continue the filter chain with the modified request containing the role header
                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    } else {
                        // If the role is not USER, do not forward the request and return an error response
                        return onError(exchange);
                    }

                } catch (Exception e) {
                    // In case of token validation error, return an error response
                    return onError(exchange);
                }
            }
        }

        // If the route is not secured, just forward the request without modifying it
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

    private boolean isUser(Claims claims){



        return "USER".equals(claims.get("role", String.class));

        //return claims.get("role").equals("USER");
    }


}