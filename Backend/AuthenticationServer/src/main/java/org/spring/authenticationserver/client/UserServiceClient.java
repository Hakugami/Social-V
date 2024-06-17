package org.spring.authenticationserver.client;

import org.spring.authenticationserver.models.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

	@GetMapping("/user/{username}")
	UserModel getUserByUsername(@PathVariable("username") String username);

}
