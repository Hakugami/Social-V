package org.spring.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.authenticationserver.models.AuthenticationResponse;
import org.spring.authenticationserver.models.CustomUserDetails;
import org.spring.authenticationserver.utils.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

	private static final Logger log = LoggerFactory.getLogger(JwtService.class);
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	public AuthenticationResponse refresh(String refreshToken) {
		refreshToken = jwtUtil.removeBearer(refreshToken);
		// Validate the refresh token
		if (jwtUtil.isTokenExpired(refreshToken)) {
			throw new RuntimeException("Refresh token is expired");
		}

		// Extract username from refresh token
		String username = jwtUtil.getUsernameFromToken(refreshToken);

		// Assuming you have a method to load user details by username
		CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

		log.info("User details fetched: {}", userDetails);

		// Generate new JWT token and refresh token
		String newJwt = jwtUtil.generateToken(userDetails);
		String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

		return new AuthenticationResponse(newJwt, newRefreshToken);
	}
}
