package org.spring.apigateway.filters;

import org.spring.apigateway.clients.AuthServerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

@Component
public class JwtValidationFilter implements Function<ServerRequest, ServerRequest> {

	private final AuthServerClient authServerClient;

	public JwtValidationFilter(AuthServerClient authServerClient) {
		this.authServerClient = authServerClient;
	}

	@Override
	public ServerRequest apply(ServerRequest request) {
		// Extract the Authorization header
		String authHeader = request.headers().firstHeader("Authorization");

		// Check if the Authorization header is present and starts with "Bearer "
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// Respond with an error response (this is not directly possible here, so we throw an exception)
			throw new RuntimeException("Unauthorized: Missing or invalid JWT token");
		}

		// Validate the JWT token using the external authentication server
		boolean isValid = authServerClient.validateToken(authHeader);

		if (!isValid) {
			// Respond with an error response (this is not directly possible here, so we throw an exception)
			throw new RuntimeException("Unauthorized: Invalid JWT token");
		}

		// Proceed with the request
		return request;
	}
}
