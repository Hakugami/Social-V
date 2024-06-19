package org.spring.apigateway.configs;

import org.spring.apigateway.security.CustomReactiveAuthenticationManager;
import org.spring.apigateway.security.UsernamePasswordAuthenticationTokenServerAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private final CustomReactiveAuthenticationManager customReactiveAuthenticationManager;

	public SecurityConfig(CustomReactiveAuthenticationManager customReactiveAuthenticationManager) {
		this.customReactiveAuthenticationManager = customReactiveAuthenticationManager;
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(customReactiveAuthenticationManager);
		authenticationWebFilter.setServerAuthenticationConverter(new UsernamePasswordAuthenticationTokenServerAuthenticationConverter());

		http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/eureka/**").permitAll()
						.pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
						.anyExchange().authenticated())
				.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));

		return http.build();
	}






}
