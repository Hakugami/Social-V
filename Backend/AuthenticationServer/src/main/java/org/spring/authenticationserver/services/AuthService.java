package org.spring.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.spring.authenticationserver.client.UserServiceClient;
import org.spring.authenticationserver.models.AuthenticationRequest;
import org.spring.authenticationserver.models.AuthenticationResponse;
import org.spring.authenticationserver.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;


	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		// Authenticate the user with the AuthenticationManager
		Authentication authentication = authenticate(
				authenticationRequest.username(),
				authenticationRequest.password()
		);

		// Get the authenticated user details
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Generate the JWT token
		String jwt = jwtUtil.generateToken(userDetails);

		return new AuthenticationResponse(jwt);
	}

	private Authentication authenticate(String username, String password) {
		try {
			// Create an authentication token with the provided credentials
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(username, password);

			// Authenticate the user with the AuthenticationManager
			return authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException ex) {
			// Handle invalid credentials
			throw new RuntimeException("Invalid credentials");
		}
	}


}