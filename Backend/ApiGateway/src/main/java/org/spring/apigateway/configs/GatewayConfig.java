package org.spring.apigateway.configs;



import lombok.RequiredArgsConstructor;
import org.spring.apigateway.filters.JwtValidationFilter;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;


@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

	private final JwtValidationFilter jwtValidationFilter;

	@Bean
	public RouterFunction<ServerResponse> usersRouter() {
		return GatewayRouterFunctions.route("user-service")
				.route(RequestPredicates.path("/users/**"), HandlerFunctions.http())
				.before(rewritePath("/users/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("user-service"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> authRouter() {
		return GatewayRouterFunctions.route("authentication-server")
				.route(RequestPredicates.path("/auth/**"), HandlerFunctions.http())
				.before(rewritePath("/auth/(?<segment>.*)", "/${segment}"))
				.filter(lb("authentication-server"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> postsRouter() {
		return GatewayRouterFunctions.route("post-service")
				.route(RequestPredicates.path("/posts/**"), HandlerFunctions.http())
				.before(rewritePath("/posts/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("post-service"))
				.build();
	}

}
