package org.spring.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.spring.authenticationserver.client.UserServiceClient;
import org.spring.authenticationserver.models.AuthenticationRequest;
import org.spring.authenticationserver.models.AuthenticationResponse;
import org.spring.authenticationserver.models.UserModel;
import org.spring.authenticationserver.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

	private final JwtUtil jwtUtil;
	private final UserServiceClient userServiceClient;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch the user details from the UserServiceClient
		UserModel user = userServiceClient.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// Create and return a UserDetails object with the user's credentials and empty authorities
		return new org.springframework.security.core.userdetails.User(
				user.username(),
				user.password(),
				Collections.emptyList() // Replace with proper authorities if needed
		);
	}
}