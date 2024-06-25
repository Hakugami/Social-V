package org.spring.postservice.clients;

import org.spring.postservice.configs.FeignClientConfiguration;
import org.spring.postservice.models.Dtos.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "comment-service", configuration = FeignClientConfiguration.class)
public interface CommentServiceClient {

	@GetMapping("/api/v1/comments/{postId}")
	List<CommentDto> getComments(@PathVariable("postId") String postId);
}
