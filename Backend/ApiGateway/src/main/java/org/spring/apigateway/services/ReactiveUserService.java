package org.spring.apigateway.services;

import lombok.RequiredArgsConstructor;
import org.spring.apigateway.models.AuthModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReactiveUserService {

	private final UserServiceClient userServiceClient;

	public Mono<AuthModel> getUserByUsername(String username) {
		return userServiceClient.getUserByUsername(username);
	}


}
