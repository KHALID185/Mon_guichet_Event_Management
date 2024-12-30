package org.gatewaymicroservice.filter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
@Slf4j
public class AdminJwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;
    private final RouterValidator routerValidator;

    /*@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, "Authorization header is missing");
            }

            String token = extractToken(request);
            if (token == null) {
                return onError(exchange, "Bearer token is missing");
            }

            try {
                if (!isAdmin(token)) {
                    return onError(exchange, "Access Denied: Not an Admin");
                }
            } catch (Exception e) {
                log.error("Error parsing token: {}", e.getMessage());
                return onError(exchange, "Invalid token");
            }
        }

        return chain.filter(exchange);
    }

    private boolean isAdmin(Claims claims) {
        // Extract the roles claim which is a list containing a single string.
        List<String> roles = claims.get("roles", List.class);
        if (roles == null || roles.isEmpty()) {
            // No roles found in the token
            log.info("No roles found in the token");
            return false;
        }

        // Get the first role string from the list and remove the brackets and "RoleDTO" part
        String roleStr = roles.get(0).replace("[", "").replace("]", "").replace("RoleDTO", "");

        // Now, we expect the string to be something like "(id=1, role=ADMIN)"
        // Let's use a simple regex to extract the role name
        Pattern rolePattern = Pattern.compile("role=([A-Z]+)");
        Matcher matcher = rolePattern.matcher(roleStr);

        if (matcher.find()) {
            String roleName = matcher.group(1);
            // Check if the extracted role name is ADMIN
            return "ADMIN".equals(roleName);
        }

        return false;
    }



    private String extractToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().getOrEmpty("Authorization");
        if (headers.isEmpty()) {
            return null;
        }
        String authorizationHeader = headers.get(0);
        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        log.error("Authentication error: {}", error);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}

*/



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, "Authorization header is missing");
            }

            String token = extractToken(request);
            if (token == null) {
                return onError(exchange, "Bearer token is missing");
            }

            try {
                Claims claims = jwtUtil.getClaims(token);
                if (!isAdmin(claims)) {
                    return onError(exchange, "Access Denied: Not an Admin");
                }
            } catch (Exception e) {
                log.error("Error parsing token: {}", e.getMessage());
                return onError(exchange, "Invalid token");
            }
        }

        return chain.filter(exchange);
    }

    private boolean isAdmin(Claims claims) {
        List<String> roles = claims.get("roles", List.class);
        if (roles == null || roles.isEmpty()) {
            log.info("No roles found in token");
            return false;
        }

        String roleStr = roles.get(0).toString();
        Pattern pattern = Pattern.compile("role=([A-Z]+)");
        Matcher matcher = pattern.matcher(roleStr);

        while (matcher.find()) {
            if ("ADMIN".equals(matcher.group(1))) {
                return true;
            }
        }
        return false;
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().getOrEmpty("Authorization");
        if (headers.isEmpty()) {
            return null;
        }
        String authorizationHeader = headers.get(0);
        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        log.error("Authentication error: {}", error);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}