package org.spring.postservice.clients;

import org.spring.postservice.configs.FeignClientConfiguration;
import org.spring.postservice.models.Dtos.PictureDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service" , configuration = FeignClientConfiguration.class)
public interface UserServiceClient {

	@GetMapping("/api/v1/users/loadPicAndName/{username}")
	PictureDto loadPicAndName(@PathVariable String username);



}
