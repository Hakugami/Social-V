package org.spring.authenticationserver.client;

import org.spring.authenticationserver.models.AuthModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

	@GetMapping("/api/v1/users/auth/email/{email}")
	AuthModel getUserByEmail(@PathVariable("email") String email);

}
