package org.spring.apigateway.services;

import lombok.RequiredArgsConstructor;
import org.spring.apigateway.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

	private final WebClient.Builder webClient;


	public Mono<AuthModel> getUserByUsername(String username) {
		return webClient.build()
				.get()
				.uri("http://userservice/api/v1/users/auth/" + username)
				.retrieve()
				.bodyToMono(AuthModel.class);
	}
}
