package org.spring.apigateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.apigateway.models.AuthenticationRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class UsernamePasswordAuthenticationTokenServerAuthenticationConverter implements ServerAuthenticationConverter {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		return exchange.getRequest().getBody()
				.next()
				.flatMap(dataBuffer -> {
					String body = dataBuffer.toString(StandardCharsets.UTF_8);
					try {
						AuthenticationRequest authRequest = objectMapper.readValue(body, AuthenticationRequest.class);
						return Mono.just(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
					} catch (Exception e) {
						return Mono.error(e);
					}
				});
	}
}
