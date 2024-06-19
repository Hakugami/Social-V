package org.spring.postservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.postservice.models.Dtos.ImagePostDto;
import org.spring.postservice.models.Dtos.PostDto;
import org.spring.postservice.models.Dtos.PostResponse;
import org.spring.postservice.models.Dtos.VideoPostDto;
import org.spring.postservice.models.PostModel;
import org.spring.postservice.repositories.PostRepository;
import org.spring.postservice.utils.FirebaseUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final FirebaseUtil firebaseUtil;

	public void saveTextPost(PostDto textPostDto) {
		log.info("Saving text post: {}", textPostDto);
		PostModel postModel = savePost(textPostDto);
		postRepository.save(postModel);
	}

	public void saveVideoPost(VideoPostDto videoPost) {
		log.info("Saving video post: {}", videoPost);
		PostModel postModel = savePost(videoPost);
		//TODO upload the video to firebase first and then save the url in the database
		String url = firebaseUtil.uploadVideo(videoPost.getVideo());
		postModel.setVideoUrl(url);
		postRepository.save(postModel);
	}

	public void saveImagePost(ImagePostDto imagePostDto) {
		log.info("Saving image post: {}", imagePostDto);
		PostModel postModel = savePost(imagePostDto);
		//TODO upload the images to firebase first and then save the urls in the database
		List<String> url = firebaseUtil.uploadImage(imagePostDto.getImages());
		postModel.setImageUrl(url);
		postRepository.save(postModel);
	}


	public List<PostResponse> getTextPost(String id , int page, int size) {
		log.info("Getting text post by id: {}", id);
		List<PostModel> postModel = postRepository.findByIdAndType(id , PageRequest.of(page,size),"Text");
		if (postModel.isEmpty()) {
			throw new RuntimeException("No posts found for user id: " + id);
		}
		return postModel.stream().map(this::getPostResponse).collect(Collectors.toList());
	}

	public List<PostResponse> getVideoPost(String id, int page, int size) {
		log.info("Getting video post by id: {}", id);
		List<PostModel> postModel = postRepository.findByIdAndType(id, PageRequest.of(page, size),"Video");
		if (postModel.isEmpty()) {
			throw new RuntimeException("No posts found for user id: " + id);
		}
		return postModel.stream().map(this::getPostResponse).collect(Collectors.toList());
	}

	public List<PostResponse> getImagePost(String id, int page, int size) {
		log.info("Getting image post by id: {}", id);
		List<PostModel> postModel = postRepository.findByIdAndType(id, PageRequest.of(page, size),"Image");
		if (postModel.isEmpty()) {
			throw new RuntimeException("No posts found for user id: " + id);
		}
		return postModel.stream().map(this::getPostResponse).collect(Collectors.toList());
	}

	private PostModel savePost(PostDto postDto) {
		PostModel postModel = new PostModel();
		postModel.setUserId(postDto.getUserId());
		postModel.setUsername(postDto.getUsername());
		postModel.setContent(postDto.getContent());
		postModel.setCreatedAt(postDto.getCreatedAt());
		return postModel;
	}


	public List<PostResponse> getPostsByUserId(String id, int page, int size) {
		log.info("Getting all posts by user id: {}", id);
		List<PostModel> postModel = postRepository.findByUserId(id, PageRequest.of(page, size));
		if (postModel.isEmpty()) {
			throw new RuntimeException("No posts found for user id: " + id);
		}
		return postModel.stream().map(this::getPostResponse).collect(Collectors.toList());


	}

	public List<PostResponse> getAllPosts(int page, int size) {
		log.info("Getting all posts");
		return postRepository.findAllBy(PageRequest.of(page, size)).stream().map(this::getPostResponse).collect(Collectors.toList());

	}

	private PostResponse getPostResponse(PostModel postModel) {
		PostResponse postDto = new PostResponse();
		postDto.setUserId(postModel.getUserId());
		postDto.setUsername(postModel.getUsername());
		postDto.setContent(postModel.getContent());
		postDto.setCreatedAt(postModel.getCreatedAt());
		postDto.setImagesUrl(postModel.getImageUrl());
		postDto.setVideoUrl(postModel.getVideoUrl());
		return postDto;
	}
}
