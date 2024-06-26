package org.spring.commentservice.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.commentservice.models.CommentModel;
import org.spring.commentservice.models.Dtos.CommentDto;
import org.spring.commentservice.models.Dtos.CommentRequest;
import org.spring.commentservice.services.CommentService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@Slf4j
@RequiredArgsConstructor

public class CommentController {

	private final CommentService commentService;

	@GetMapping("/{id}")
	@ApiResponse(description = "Get comments by post id", responseCode = "200")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("id") String postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		log.info("Received request to get comments for post with id: {}", postId);
		List<CommentDto> commentDtos = commentService.getCommentByPostId(postId, page, size);
		if (commentDtos != null && !commentDtos.isEmpty()) {
			return ResponseEntity.ok(commentDtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/")
	@ApiResponse(description = "Get all comments", responseCode = "200")
	public ResponseEntity<CollectionModel<EntityModel<CommentDto>>> getAllComments(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.info("Received request to get all comments with page: {} and size: {}", page, size);
		List<EntityModel<CommentDto>> comments = commentService.getAllComments(page, size).stream()
				.map(commentDto -> EntityModel.of(commentDto,
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getCommentsByPostId(commentDto.getId(), 0, 10)).withSelfRel(),
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllComments(page, size)).withRel("comments")
				))
				.collect(Collectors.toList());

		CollectionModel<EntityModel<CommentDto>> collectionModel = CollectionModel.of(comments,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllComments(page, size)).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllComments(page, size)).withRel("comments")
		);

		return ResponseEntity.ok(collectionModel);
	}

	@PostMapping("/")
	@ApiResponse(description = "Adding comment to a post", responseCode = "201")
	public ResponseEntity<CommentModel> addComment(@RequestBody CommentRequest commentDto) {
		log.info("Received request to add comment: {}", commentDto);
		CommentModel savedComment = commentService.addComment(commentDto);
		return ResponseEntity.ok(savedComment);


	}
}
