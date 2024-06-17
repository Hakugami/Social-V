package org.spring.authenticationserver.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.authenticationserver.filters.JwtRequestFilter;
import org.spring.authenticationserver.models.AuthenticationRequest;
import org.spring.authenticationserver.models.AuthenticationResponse;
import org.spring.authenticationserver.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtRequestFilter jwtRequestFilter;


	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
		AuthenticationResponse authenticationResponse = authService.authenticate(authenticationRequest);
		return ResponseEntity.ok(authenticationResponse);
	}

	@PostMapping("/validate-token")
	public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token){
		String jwt = token.substring(7); // Extract the token from the header
		boolean isValid = jwtRequestFilter.validateAndSetAuthentication(jwt, null);
		return ResponseEntity.ok(isValid);
	}
}