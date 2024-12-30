package org.gatewaymicroservice.config;

import lombok.AllArgsConstructor;
import org.gatewaymicroservice.filter.AdminJwtAuthenticationFilter;
import org.gatewaymicroservice.filter.JwtAuthenticationFilter;

import org.gatewaymicroservice.filter.UserJwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@AllArgsConstructor
public class ConfigRouter {
    private final AdminJwtAuthenticationFilter adminFilter;
    private final UserJwtAuthenticationFilter userFilter;
    private final JwtAuthenticationFilter jwtGlobalFilter;


    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-microservice", r -> r.path("/users/**")
                        .filters(f -> f.filter(adminFilter))
                        .uri("lb://user-microservice"))

                .route("auth-microservice", r -> r.path("/auth/**")
                        .filters(f->f.filter(jwtGlobalFilter))
                        .uri("lb://auth-microservice"))

//                .route("catalogue-microservice", r -> r.path("/categories/**")
//                        .filters(f->f.filter(adminFilter))
//                        .uri("lb://catalogue-microservice"))
//
//                .route("order-microservice", r-> r.path("/orders/**")
//                        .filters(f->f.filter(adminFilter))
//                        .uri("lb://order-microservice"))






//                .route("user2-microservice",r -> r.path("/api/v1/user/**")
//                        .filters(f -> f.filter(adminFilter))
//                        .uri("lb://user2-microservice")
//                )


//                .route("security-microservice", r -> r.path("/auth/**")
//                        .filters(f -> f.filter(adminFilter))
//                        .uri("lb://security-microservice"))


//                .route("order-microservice",r -> r.path("/api/order/**")
//                        .filters(f -> f.rewritePath(
//                                "/api/order",
//                                "/order/NotAllowed"))
//                        .uri("lb://order-microservice")
//                )
//                .route("order2-microservice",r -> r.path("/api/order2/**")
//                        .filters(f -> f.rewritePath(
//                                "/api/order2",
//                                "/order/NotAllowed"))
//                        .uri("lb://order2-microservice")
//                )


                .route("order2-microservice",r -> r.path("/api/order2/**")
                        .filters(f->f.filter(adminFilter))
                        .uri("lb://order2-microservice")
                )


//                .route("payment-microservice",r -> r.path("/api/payments/**")
//                        .filters(f -> f.rewritePath(
//                                "/api/payments",
//                                "/payments/NotAllowed"))
//                        .uri("lb://payment-microservice")
//                )


                .route("payment-microservice",r -> r.path("/api/payments/**")
                        .filters(f->f.filter(adminFilter))
                        .uri("lb://payment-microservice")
                )


//                .route("review-microservice",r -> r.path("/api/v1/review/**")
//                        .filters(f -> f.rewritePath(
//                                "/api/v1/review",
//                                "/api/v1/review/NotAllowed"))
//                        .uri("lb://review-microservice")
//                )


                .route("review-microservice",r -> r.path("/api/v1/review/**")
                        .filters(f->f.filter(adminFilter))
                        .uri("lb://review-microservice")
                )


                .route("catalog-microservice",r -> r.path("/api/v1/category/**")
                        .filters(f->f.filter(adminFilter))
                        .uri("lb://catalog-microservice")
                )


                .build();
    }
}
