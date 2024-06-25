package org.spring.postservice.clients;

import org.spring.postservice.configs.FeignClientConfiguration;
import org.spring.postservice.models.Dtos.LikeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "like-service", configuration = FeignClientConfiguration.class)
public interface LikeServiceClient {

	@GetMapping("/api/v1/likes/{postId}")
	LikeDto getLikes(@PathVariable("postId") String postId);
}
