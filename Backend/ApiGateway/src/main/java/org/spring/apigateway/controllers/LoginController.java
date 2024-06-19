package org.spring.apigateway.controllers;

import lombok.extern.slf4j.Slf4j;
import org.spring.apigateway.models.AuthenticationRequest;
import org.spring.apigateway.models.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

	@PostMapping("/login")
	public Mono<ResponseEntity<AuthenticationResponse>> login(@RequestBody AuthenticationRequest loginRequest, Authentication authentication) {
		log.info("Received login request for user: {}", loginRequest.username());

		// Log authentication details if available
		if (authentication instanceof JwtAuthenticationToken) {
			log.debug("JWT token received: {}", ((JwtAuthenticationToken) authentication).getToken().getTokenValue());
		} else {
			log.debug("No JWT token found in authentication.");
		}

		String token = null;
		if (authentication instanceof JwtAuthenticationToken) {
			token = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
		}
		return Mono.just(ResponseEntity.ok(new AuthenticationResponse(token)));
	}
}
