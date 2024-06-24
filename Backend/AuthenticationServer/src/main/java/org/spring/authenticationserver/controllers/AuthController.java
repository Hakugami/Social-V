package org.spring.authenticationserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.authenticationserver.filters.JwtRequestFilter;
import org.spring.authenticationserver.models.AuthenticationRequest;
import org.spring.authenticationserver.models.AuthenticationResponse;
import org.spring.authenticationserver.services.AuthService;
import org.spring.authenticationserver.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;


	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
		log.info("Received login request for user: {}", authenticationRequest.email());
		AuthenticationResponse authenticationResponse = authService.authenticate(authenticationRequest);
		return ResponseEntity.ok(authenticationResponse);
	}

	@PostMapping("/validate")
	public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
		log.info("Received request to validate token");
		return ResponseEntity.ok(true);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthenticationResponse> refresh(@RequestHeader("Authorization") String token) {
		log.info("Received request to refresh token");
		AuthenticationResponse authenticationResponse = jwtService.refresh(token);
		return ResponseEntity.ok(authenticationResponse);
	}
}
