package org.spring.postservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.postservice.clients.CommentServiceClient;
import org.spring.postservice.clients.LikeServiceClient;
import org.spring.postservice.models.Dtos.ImagePostDto;
import org.spring.postservice.models.Dtos.PostDto;
import org.spring.postservice.models.Dtos.PostResponse;
import org.spring.postservice.models.Dtos.VideoPostDto;
import org.spring.postservice.models.PostModel;
import org.spring.postservice.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

	public PostModel savePost(PostDto postDto) {
		log.info("Saving post: {}", postDto);
		PostModel postModel = toPostModel(postDto);
		return postRepository.save(postModel);
	}

	public CompletableFuture<PostModel> saveVideoPost(VideoPostDto videoPostDto) {
		log.info("Saving video post: {}", videoPostDto);
		CompletableFuture<List<String>> futureUrl = uploadClient.uploadFile(List.of(videoPostDto.getVideo()));
		PostModel postModel = toPostModel(videoPostDto);

		return futureUrl
				.thenApply(urls -> {
					if (urls.isEmpty()) {
						throw new RuntimeException("No URL returned from video upload");
					}
					postModel.setVideoUrl(urls.get(0));
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
		postResponse.setLikes(likeServiceClient.getLikes(postResponse.getUserId()));
		postResponse.setComments(commentServiceClient.getComments(postResponse.getUserId()));
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
				.userId(postModel.getUserId())
				.username(postModel.getUsername())
				.content(postModel.getContent())
				.createdAt(postModel.getCreatedAt())
				.imagesUrl(postModel.getImageUrl())
				.videoUrl(postModel.getVideoUrl())
				.build();
	}
}
