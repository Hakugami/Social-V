package org.spring.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.authenticationserver.client.UserServiceClient;
import org.spring.authenticationserver.models.AuthModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserServiceClient userServiceClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch the user details from the UserServiceClient
		log.info("Fetching user details for user: {}", username);
		AuthModel user = userServiceClient.getUserByEmail(username);
		log.info("User details fetched: {}", user);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// Create and return a UserDetails object with the user's credentials and empty authorities
		return new org.springframework.security.core.userdetails.User(
				user.email(),
				user.password(),
				Collections.emptyList() // Replace with proper authorities if needed
		);
	}
}
