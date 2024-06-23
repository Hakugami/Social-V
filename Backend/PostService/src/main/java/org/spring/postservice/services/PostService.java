package org.spring.postservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.postservice.clients.CommentServiceClient;
import org.spring.postservice.clients.LikeServiceClient;
import org.spring.postservice.clients.UserServiceClient;
import org.spring.postservice.events.PostCreatedEvent;
import org.spring.postservice.models.Dtos.*;
import org.spring.postservice.models.PostModel;
import org.spring.postservice.models.enums.ContentType;
import org.spring.postservice.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final UploadService uploadClient;
	private final LikeServiceClient likeServiceClient;
	private final CommentServiceClient commentServiceClient;
	private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;
	private final UserServiceClient userServiceClient;


	public PostModel savePost(PostDto postDto) {
		log.info("Saving post: {}", postDto);
		PostModel postModel = toPostModel(postDto);
		postModel.setCreatedAt(LocalDateTime.from(Instant.now()));
		postModel.setType(ContentType.TEXT);
		postRepository.save(postModel);
		kafkaTemplate.send("post-topic", new PostCreatedEvent(postModel.getId()));
		return postModel;
	}

	public CompletableFuture<PostModel> saveVideoPost(VideoPostDto videoPostDto) {
		log.info("Saving video post: {}", videoPostDto);
		CompletableFuture<List<String>> futureUrl = uploadClient.uploadFile(List.of(videoPostDto.getVideo()));
		PostModel postModel = toPostModel(videoPostDto);
		postModel.setType(ContentType.VIDEO);

		return futureUrl
				.thenApply(urls -> {
					if (urls.isEmpty()) {
						throw new RuntimeException("No URL returned from video upload");
					}
					postModel.setVideoUrl(urls.getFirst());
					return postModel;
				})
				.thenApply(postRepository::save)
				.exceptionally(e -> {
					log.error("Error saving video post", e);
					throw new RuntimeException("Error saving video post", e);
				});
	}

	public CompletableFuture<PostModel> saveImagePost(ImagePostDto imagePostDto) {
		log.info("Saving image post: {}", imagePostDto);
		CompletableFuture<List<String>> futureUrls = uploadClient.uploadFile(imagePostDto.getImages());
		PostModel postModel = toPostModel(imagePostDto);
		postModel.setType(ContentType.IMAGE);

		return futureUrls
				.thenApply(urls -> {
					postModel.setImageUrl(urls);
					return postModel;
				})
				.thenApply(postRepository::save)
				.exceptionally(e -> {
					log.error("Error saving image post", e);
					throw new RuntimeException("Error saving image post", e);
				});
	}

	public List<PostResponse> getPosts(String userId, String type, int page, int size) {
		log.info("Getting {} posts by user id: {}", type, userId);
		List<PostModel> postModels = postRepository.findByIdAndType(userId, PageRequest.of(page, size), type);
		return toPostResponses(userId, postModels);
	}

	public List<PostResponse> getPostsByUserId(String userId, int page, int size) {
		log.info("Getting all posts by user id: {}", userId);
		List<PostModel> postModels = postRepository.findByUserId(userId, PageRequest.of(page, size));
		return toPostResponses(userId, postModels);
	}

	public List<PostResponse> getAllPosts(int page, int size) {
		log.info("Getting all posts");
		return postRepository.findAllBy(PageRequest.of(page, size)).stream()
				.map(this::toPostResponse)
				.map(this::populateLikesAndComments)
				.toList();
	}

	private List<PostResponse> toPostResponses(String userId, List<PostModel> postModels) {
		return Optional.ofNullable(postModels)
				.orElseThrow(() -> new RuntimeException("No posts found for user id: " + userId))
				.stream()
				.map(this::toPostResponse)
				.map(this::populateLikesAndComments)
				.toList();
	}

	private PostResponse populateLikesAndComments(PostResponse postResponse) {
		log.info("Populating likes and comments for post: {}", postResponse.getId());
		try {
			postResponse.setLikes(likeServiceClient.getLikes(postResponse.getId()));
			postResponse.setComments(commentServiceClient.getComments(postResponse.getId()));
			loadCommentProfilePicture(postResponse);
		} catch (ResponseStatusException e) {
			if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
				log.warn("Comments not found for post: {}", postResponse.getId());
				postResponse.setComments(List.of()); // Set empty list for comments
			} else {
				log.error("Error fetching likes or comments for post: {}", postResponse.getId(), e);
				// Handle other HTTP status codes if needed
			}
		} catch (Exception e) {
			log.error("Unexpected error populating likes and comments for post: {}", postResponse.getId(), e);
			// Handle unexpected exceptions
		}
		return postResponse;
	}


	private PostModel toPostModel(PostDto postDto) {
		return PostModel.builder()
				.userId(postDto.getUserId())
				.username(postDto.getUsername())
				.content(postDto.getContent())
				.createdAt(postDto.getCreatedAt())
				.build();
	}

	private PostResponse toPostResponse(PostModel postModel) {
		return PostResponse.builder()
				.id(postModel.getId())
				.userId(postModel.getUserId())
				.username(postModel.getUsername())
				.content(postModel.getContent())
				.createdAt(postModel.getCreatedAt())
				.imagesUrl(postModel.getImageUrl())
				.videoUrl(postModel.getVideoUrl())
				.profilePicture(loadPostProfilePicture(postModel.getUsername()))
				.build();
	}

	private void loadCommentProfilePicture(PostResponse postResponse) {
		log.info("Loading profile picture for post: {}", postResponse.getId());
		try {
			postResponse.getComments().forEach(commentDto -> {
				PictureDto pictureDto = userServiceClient.loadPicAndName(commentDto.getUsername());
				commentDto.setProfilePicture(pictureDto.profilePicture());
			});
		} catch (RuntimeException e) {
			log.error("Error loading profile picture for post: {}", postResponse.getId(), e);
			// Handle exception
		}
	}

	private String loadPostProfilePicture(String username) {
		log.info("Loading profile picture for post: {}", username);
		try {
			PictureDto pictureDto = userServiceClient.loadPicAndName(username);
			return pictureDto.profilePicture();
		} catch (RuntimeException e) {
			log.error("Error loading profile picture for post: {}", username, e);
			// Handle exception
			return null;
		}
	}

}
