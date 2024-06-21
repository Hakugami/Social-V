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
		return GatewayRouterFunctions.route("USERSERVICE")
				.route(RequestPredicates.path("/users/**"), HandlerFunctions.http())
				.before(rewritePath("/users/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if dont want to authentication check
				.filter(lb("USERSERVICE"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> authRouter() {
		return GatewayRouterFunctions.route("AUTHSERVICE")
				.route(RequestPredicates.path("/auth/**"), HandlerFunctions.http())
				.before(rewritePath("/auth/(?<segment>.*)", "/${segment}"))
				.filter(lb("AUTHENTICATIONSERVER"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> friendsRouter() {
		return GatewayRouterFunctions.route("FRIENDSERVICE")
				.route(RequestPredicates.path("/friends/**"), HandlerFunctions.http())
				.before(rewritePath("/friends/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to check authentication
				.filter(lb("FRIENDSERVICE"))
				.build();
	}

}
