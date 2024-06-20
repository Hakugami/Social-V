package org.spring.apigateway.configs;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	private static void userServiceRoute(RouteLocatorBuilder.Builder builder) {
		builder
				.route("user-service", r -> r.path("/users/**")
						.filters(f -> f.rewritePath("/users/(?<segment>.*)", "/${segment}"))
						.uri("lb://userservice"));
	}

	private static void authServiceRoute(RouteLocatorBuilder.Builder builder) {
		builder
				.route("auth-service", r -> r.path("/auth/**")
						.filters(f -> f.rewritePath("/auth/(?<segment>.*)", "/${segment}"))
						.uri("lb://authenticationserver"));
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		RouteLocatorBuilder.Builder routes = builder.routes();
		userServiceRoute(routes);
		authServiceRoute(routes);
		return routes.build();
	}

}
