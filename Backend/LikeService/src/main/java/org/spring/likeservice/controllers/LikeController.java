package org.spring.likeservice.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.likeservice.models.Dtos.LikeDto;
import org.spring.likeservice.models.Dtos.LikeRequest;
import org.spring.likeservice.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/likes")
@Slf4j
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/")
	@ApiResponse(description = "Add like to post", responseCode = "201")
	public ResponseEntity<LikeDto> addLike(@RequestBody LikeRequest likeRequest) {
		log.info("Received request to add like by user: {} for post: {}", likeRequest.userId(), likeRequest.postId());
		LikeDto likeDto = likeService.addLike(likeRequest);
		return ResponseEntity.ok(likeDto);


	}

	@GetMapping("/{postId}")
	@ApiResponse(description = "Get likes by post id", responseCode = "200")
	public ResponseEntity<LikeDto> getLikesByPostId(@PathVariable String postId) {
		log.info("Received request to get likes for post id: {}", postId);
		LikeDto likeDto = likeService.getLikesByPostId(postId);
		return ResponseEntity.ok(likeDto);


	}

	@DeleteMapping("/{likeId}")
	@ApiResponse(description = "Remove like by like id", responseCode = "200")
	public ResponseEntity<Boolean> removeLike(@PathVariable String likeId) {
		log.info("Received request to remove like with id: {}", likeId);
		boolean status = likeService.removeLike(likeId);
		return ResponseEntity.ok(status);


	}

	@PutMapping("/{likeId}")
	@ApiResponse(description = "Update like by like id", responseCode = "200")
	public ResponseEntity<Boolean> updateLike(@PathVariable String likeId, @RequestBody LikeRequest likeRequest) {
		log.info("Received request to update like with id: {}", likeId);
		boolean status = likeService.updateLike(likeId, likeRequest);
		return ResponseEntity.ok(status);
	}


}
