package org.spring.apigateway.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.apigateway.services.ReactiveUserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {


	private final ReactiveUserService userServiceClient;
	private final PasswordEncoder passwordEncoder;


	@Override
	public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();

		return userServiceClient.getUserByUsername(username)
				.flatMap(user -> {
					if (user == null || !matchPassword(rawPassword, user.password())) {
						log.error("Invalid username or password.");
						return Mono.error(new UsernameNotFoundException("Invalid username or password."));
					}
					log.info("User authenticated: {}", user);
					return Mono.just(new UsernamePasswordAuthenticationToken(username, rawPassword, Collections.emptyList()));
				});
	}

	private boolean matchPassword(String rawPassword, String encodedPassword) {
		log.info("raw password: {}, encoded password: {}", rawPassword, encodedPassword);
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
