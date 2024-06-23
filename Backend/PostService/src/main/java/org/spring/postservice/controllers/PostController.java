package org.spring.postservice.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.postservice.models.Dtos.ImagePostDto;
import org.spring.postservice.models.Dtos.PostDto;
import org.spring.postservice.models.Dtos.PostResponse;
import org.spring.postservice.models.Dtos.VideoPostDto;
import org.spring.postservice.models.PostModel;
import org.spring.postservice.services.PostService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/posts")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

	private final PostService postService;
	private final ObjectMapper objectMapper;

	@PostMapping("/text")
	@ApiResponse(description = "Save text post", responseCode = "201")
	public ResponseEntity<EntityModel<PostModel>> saveTextPost(@RequestBody PostDto textPostDto) {
		log.info("Received text post: {}", textPostDto);

		PostModel postModel = postService.savePost(textPostDto);

		log.info("Text post saved successfully");

		EntityModel<PostModel> resource = EntityModel.of(postModel);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).saveTextPost(textPostDto));
		resource.add(linkTo.withRel("self"));

		log.info("Returning response: {}", resource);


		return ResponseEntity.created(linkTo.toUri()).body(resource);
	}


	@PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiResponse(description = "Save video post", responseCode = "201")
	public DeferredResult<ResponseEntity<EntityModel<PostModel>>> saveVideoPost(
			@RequestPart("dto") String dtoJson,
			@RequestPart("file") MultipartFile video
	) {
		DeferredResult<ResponseEntity<EntityModel<PostModel>>> deferredResult = new DeferredResult<>();

		CompletableFuture.supplyAsync(() -> {
			try {
				VideoPostDto videoPostDto = objectMapper.readValue(dtoJson, VideoPostDto.class);
				videoPostDto.setVideo(video);
				return postService.saveVideoPost(videoPostDto);
			} catch (Exception e) {
				throw new RuntimeException("Error processing request", e);
			}
		}).thenAccept(postModelFuture -> postModelFuture.thenAccept(postModel -> {
			EntityModel<PostModel> resource = EntityModel.of(postModel);
			WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).saveVideoPost(dtoJson, video));
			resource.add(linkTo.withRel("self"));
			deferredResult.setResult(ResponseEntity.created(linkTo.toUri()).body(resource));
		})).exceptionally(ex -> {
			deferredResult.setErrorResult(
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Error occurred: " + ex.getMessage())
			);
			return null;
		});

		return deferredResult;
	}

	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiResponse(description = "Save image post", responseCode = "201")
	public DeferredResult<ResponseEntity<EntityModel<PostModel>>> saveImagePost(
			@RequestPart("dto") String dtoJson,
			@RequestPart("file") List<MultipartFile> images
	) {
		DeferredResult<ResponseEntity<EntityModel<PostModel>>> deferredResult = new DeferredResult<>();

		CompletableFuture.supplyAsync(() -> {
			try {
				ImagePostDto imagePostDto = objectMapper.readValue(dtoJson, ImagePostDto.class);
				imagePostDto.setImages(images);
				return postService.saveImagePost(imagePostDto);
			} catch (Exception e) {
				throw new RuntimeException("Error processing request", e);
			}
		}).thenAccept(postModelFuture -> postModelFuture.thenAccept(postModel -> {
			EntityModel<PostModel> resource = EntityModel.of(postModel);
			WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).saveImagePost(dtoJson, images));
			resource.add(linkTo.withRel("self"));
			deferredResult.setResult(ResponseEntity.created(linkTo.toUri()).body(resource));
		})).exceptionally(ex -> {
			deferredResult.setErrorResult(
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Error occurred: " + ex.getMessage())
			);
			return null;
		});

		return deferredResult;
	}

	@GetMapping("/text/{id}")
	@ApiResponse(description = "Get text post by id", responseCode = "200")
	public ResponseEntity<EntityModel<List<PostResponse>>> getTextPost(@PathVariable("id") String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> postDto = postService.getPosts(id, "Text", page, size);
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
		List<PostResponse> videoPostDto = postService.getPosts(id, "Video", page, size);
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
		List<PostResponse> imagePostDto = postService.getPosts(id, "Image", page, size);
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
	public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<PostResponse> postDto = postService.getAllPosts(page, size);
		return ResponseEntity.ok(postDto);
	}

	@PutMapping("/{id}")
	@ApiResponse(description = "Update post by id", responseCode = "200")
	public ResponseEntity<PostModel> updatePost(@PathVariable("id") String id, @RequestBody PostDto postDto) {
		PostModel postModel = postService.updatePost(id, postDto);
		return ResponseEntity.ok(postModel);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(description = "Delete post by id", responseCode = "204")
	public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

}