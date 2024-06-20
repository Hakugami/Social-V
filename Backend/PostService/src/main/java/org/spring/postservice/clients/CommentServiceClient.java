package org.spring.postservice.clients;

import org.spring.postservice.models.Dtos.CommentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "COMMENTSERVICE")
public interface CommentServiceClient {

	@GetMapping("/comments/{postId}")
	List<CommentDto> getComments(@PathVariable("postId") String postId);
}
