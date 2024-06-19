package org.spring.postservice.controllers;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.postservice.models.Dtos.ImagePostDto;
import org.spring.postservice.models.Dtos.PostDto;
import org.spring.postservice.models.Dtos.PostResponse;
import org.spring.postservice.models.Dtos.VideoPostDto;
import org.spring.postservice.services.PostService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/text")
	@ApiResponse(description = "Save text post", responseCode = "201")
	public ResponseEntity<EntityModel<String>> saveTextPost(PostDto textPostDto) {
		postService.saveTextPost(textPostDto);
		EntityModel<String> resource = EntityModel.of("Text post saved successfully");
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).saveTextPost(textPostDto));
		resource.add(linkTo.withRel("self"));
		return ResponseEntity.created(linkTo.toUri()).body(resource);
	}

	@PostMapping("/video")
	@ApiResponse(description = "Save video post", responseCode = "201")
	public ResponseEntity<EntityModel<String>> saveVideoPost(VideoPostDto videoPost) {
		postService.saveVideoPost(videoPost);
		EntityModel<String> resource = EntityModel.of("Video post saved successfully");
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).saveVideoPost(videoPost));
		resource.add(linkTo.withRel("self"));
		return ResponseEntity.created(linkTo.toUri()).body(resource);
	}

	@PostMapping("/image")
	@ApiResponse(description = "Save image post", responseCode = "201")
	public ResponseEntity<EntityModel<String>> saveImagePost(ImagePostDto imagePostDto) {
		postService.saveImagePost(imagePostDto);
		EntityModel<String> resource = EntityModel.of("Image post saved successfully");
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).saveImagePost(imagePostDto));
		resource.add(linkTo.withRel("self"));
		return ResponseEntity.created(linkTo.toUri()).body(resource);
	}

	@GetMapping("/text/{id}")
	@ApiResponse(description = "Get text post by id", responseCode = "200")
	public ResponseEntity<EntityModel<List<PostResponse>>> getTextPost(@PathVariable("id") String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> postDto = postService.getTextPost(id, page, size);
		EntityModel<List<PostResponse>> resource = EntityModel.of(postDto);

		WebMvcLinkBuilder linkToSelf = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getTextPost(id, page, size));
		resource.add(linkToSelf.withRel("self"));

		if (postDto.size() == size) { // if this page is full, there is likely a next page
			WebMvcLinkBuilder linkToNext = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getTextPost(id, page + 1, size));
			resource.add(linkToNext.withRel("next"));
		}

		if (page > 0) { // if not the first page, there is a previous page
			WebMvcLinkBuilder linkToPrev = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getTextPost(id, page - 1, size));
			resource.add(linkToPrev.withRel("prev"));
		}

		return ResponseEntity.ok(resource);
	}

	@GetMapping("/video/{id}")
	@ApiResponse(description = "Get video post by id", responseCode = "200")
	public ResponseEntity<EntityModel<List<PostResponse>>> getVideoPost(@PathVariable("id") String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> videoPostDto = postService.getVideoPost(id, page, size);
		EntityModel<List<PostResponse>> resource = EntityModel.of(videoPostDto);

		WebMvcLinkBuilder linkToSelf = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getVideoPost(id, page, size));
		resource.add(linkToSelf.withRel("self"));

		if (videoPostDto.size() == size) { // if this page is full, there is likely a next page
			WebMvcLinkBuilder linkToNext = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getVideoPost(id, page + 1, size));
			resource.add(linkToNext.withRel("next"));
		}

		if (page > 0) { // if not the first page, there is a previous page
			WebMvcLinkBuilder linkToPrev = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getVideoPost(id, page - 1, size));
			resource.add(linkToPrev.withRel("prev"));
		}

		return ResponseEntity.ok(resource);
	}

	@GetMapping("/image/{id}")
	@ApiResponse(description = "Get image post by id", responseCode = "200")
	public ResponseEntity<EntityModel<List<PostResponse>>> getImagePost(@PathVariable("id") String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> imagePostDto = postService.getImagePost(id, page, size);
		EntityModel<List<PostResponse>> resource = EntityModel.of(imagePostDto);

		WebMvcLinkBuilder linkToSelf = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getImagePost(id, page, size));
		resource.add(linkToSelf.withRel("self"));

		if (imagePostDto.size() == size) { // if this page is full, there is likely a next page
			WebMvcLinkBuilder linkToNext = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getImagePost(id, page + 1, size));
			resource.add(linkToNext.withRel("next"));
		}

		if (page > 0) { // if not the first page, there is a previous page
			WebMvcLinkBuilder linkToPrev = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getImagePost(id, page - 1, size));
			resource.add(linkToPrev.withRel("prev"));
		}

		return ResponseEntity.ok(resource);
	}

	@GetMapping("/user/{id}")
	@ApiResponse(description = "Get all posts by user id", responseCode = "200")
	public ResponseEntity<EntityModel<List<PostResponse>>> getPostsByUserId(@PathVariable("id") String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> postDto = postService.getPostsByUserId(id, page, size);
		EntityModel<List<PostResponse>> resource = EntityModel.of(postDto);

		WebMvcLinkBuilder linkToSelf = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getPostsByUserId(id, page, size));
		resource.add(linkToSelf.withRel("self"));

		if (postDto.size() == size) { // if this page is full, there is likely a next page
			WebMvcLinkBuilder linkToNext = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getPostsByUserId(id, page + 1, size));
			resource.add(linkToNext.withRel("next"));
		}

		if (page > 0) { // if not the first page, there is a previous page
			WebMvcLinkBuilder linkToPrev = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getPostsByUserId(id, page - 1, size));
			resource.add(linkToPrev.withRel("prev"));
		}

		return ResponseEntity.ok(resource);
	}

	@GetMapping("/")
	@ApiResponse(description = "Get all posts", responseCode = "200")
	public ResponseEntity<EntityModel<List<PostResponse>>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> postDto = postService.getAllPosts(page, size);
		EntityModel<List<PostResponse>> resource = EntityModel.of(postDto);

		WebMvcLinkBuilder linkToSelf = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPosts(page, size));
		resource.add(linkToSelf.withRel("self"));

		if (postDto.size() == size) { // if this page is full, there is likely a next page
			WebMvcLinkBuilder linkToNext = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPosts(page + 1, size));
			resource.add(linkToNext.withRel("next"));
		}

		if (page > 0) { // if not the first page, there is a previous page
			WebMvcLinkBuilder linkToPrev = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPosts(page - 1, size));
			resource.add(linkToPrev.withRel("prev"));
		}

		return ResponseEntity.ok(resource);
	}
}