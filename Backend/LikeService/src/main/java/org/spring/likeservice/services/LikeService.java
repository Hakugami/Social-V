package org.spring.likeservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.likeservice.controllers.LikeController;
import org.spring.likeservice.mappers.LikeMapper;
import org.spring.likeservice.models.Dtos.LikeDetailsDto;
import org.spring.likeservice.models.Dtos.LikeDto;
import org.spring.likeservice.models.Dtos.LikeRequest;
import org.spring.likeservice.models.LikeModel;
import org.spring.likeservice.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;

	public LikeDto addLike(LikeRequest likeRequest) {
		LikeModel likeModel = new LikeModel();
		likeModel.setUserId(likeRequest.userId());
		likeModel.setPostId(likeRequest.postId());
		likeModel.setUsername(likeRequest.username());
		likeModel.setEmotion(likeRequest.emotion());
		log.info("Adding like: {}", likeModel);
		likeRepository.findByUsernameAndPostId(likeModel.getUsername(), likeModel.getPostId()).ifPresentOrElse(likeModel1 -> {
		}, () -> {
			log.info("No like found for user: {} and post: {}", likeModel.getUsername(), likeModel.getPostId());
			likeRepository.save(likeModel);
		});
		return getAggregatedLikes(likeRequest.postId());
	}

	public LikeDto getLikesByPostId(String postId) {
		log.info("Getting likes for post id: {}", postId);
		return getAggregatedLikes(postId);
	}

	public boolean removeLike(String likeId) {
		log.info("Removing like with id: {}", likeId);
		likeRepository.deleteById(likeId);
		return true;
	}

	private LikeDto getAggregatedLikes(String postId) {
		List<LikeModel> likeModels = likeRepository.findByPostId(postId);

		LikeDto likeDto = new LikeDto();
		likeDto.setNumberOfLikes(likeModels.size());
		List<LikeDetailsDto> likeDetailsDtos = likeModels.stream().map(likeModel -> new LikeDetailsDto(likeModel.getUsername(), likeModel.getId(),likeModel.getEmotion())).toList();
		likeDto.setLikes(likeDetailsDtos);
		return likeDto;
	}

	public boolean updateLike(String likeId, LikeRequest likeRequest) {
		LikeModel likeModel = likeRepository.findById(likeId).orElseThrow(() -> new RuntimeException("Like not found"));
		if (likeModel == null) {
			return false;
		}
		likeModel.setEmotion(likeRequest.emotion());
		likeRepository.save(likeModel);
		return true;
	}
}
