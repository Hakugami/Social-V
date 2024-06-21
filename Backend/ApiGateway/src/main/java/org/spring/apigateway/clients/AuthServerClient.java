package org.spring.apigateway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authentication-server")
public interface AuthServerClient {

	@PostMapping("/auth/validate")
	Boolean validateToken(@RequestHeader("Authorization") String token);
}
