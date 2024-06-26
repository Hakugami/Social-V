package org.spring.apigateway.configs;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

	private final JwtValidationFilter jwtValidationFilter;

	@Bean
	public RouterFunction<ServerResponse> usersRouter() {
		return GatewayRouterFunctions.route("user-service")
				.route(RequestPredicates.path("/users/**"), HandlerFunctions.http())
				.before(rewritePath("/users/(?<segment>.*)", "/${segment}"))
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

	@Bean
	public RouterFunction<ServerResponse> friendsRouter() {
		return GatewayRouterFunctions.route("FRIENDSERVICE")
				.route(RequestPredicates.path("/friends/**"), HandlerFunctions.http())
				.before(rewritePath("/friends/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to check authentication
				.filter(lb("FRIENDSERVICE"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> likesRouter() {
		return GatewayRouterFunctions.route("like-service")
				.route(RequestPredicates.path("/likes/**"), HandlerFunctions.http())
				.before(rewritePath("/likes/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("like-service"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> searchRouter() {
		return GatewayRouterFunctions.route("search-service")
				.route(RequestPredicates.path("/search/**"), HandlerFunctions.http())
				.before(rewritePath("/search/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("SEARCHSERVICE"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> commentsRouter() {
		return GatewayRouterFunctions.route("comment-service")
				.route(RequestPredicates.path("/comments/**"), HandlerFunctions.http())
				.before(rewritePath("/comments/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("comment-service"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> recommendationRouter(){
		return GatewayRouterFunctions.route("recommendation-service")
				.route(RequestPredicates.path("/recommendations/**"), HandlerFunctions.http())
				.before(rewritePath("/recommendations/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("FRIENDSERVICE"))
				.build();
	}
	@Bean
	public RouterFunction<ServerResponse> notificationsRouter() {
		return GatewayRouterFunctions.route("notification-service")
				.route(RequestPredicates.path("/notifications/**"), HandlerFunctions.http())
				.before(rewritePath("/notifications/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("notification-service"))
				.build();
	}


	@Bean
	public RouterFunction<ServerResponse> chatRouter() {
		return GatewayRouterFunctions.route("message-service")
				.route(RequestPredicates.path("/messages/**"), HandlerFunctions.http())
				.before(rewritePath("/messages/(?<segment>.*)", "/${segment}"))
				.before(jwtValidationFilter) // remove this filter if you don't want to validate JWT
				.filter(lb("message-service"))
				.build();
	}


}
