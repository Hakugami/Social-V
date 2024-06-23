package org.spring.postservice.clients;

import org.spring.postservice.models.Dtos.LikeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LIKESERVICE")
public interface LikeServiceClient {

	@GetMapping("/likes/{postId}")
	LikeDto getLikes(@PathVariable("postId") String postId);
}
