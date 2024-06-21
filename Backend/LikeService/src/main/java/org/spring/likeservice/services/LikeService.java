package org.spring.likeservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.likeservice.mappers.LikeMapper;
import org.spring.likeservice.models.Dtos.LikeDto;
import org.spring.likeservice.models.LikeModel;
import org.spring.likeservice.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    public LikeDto addLike(String userId, String postId) {
        LikeModel likeModel = new LikeModel();
        likeModel.setUserId(userId);
        likeModel.setPostId(postId);
        log.info("Adding like: {}", likeModel);
        likeRepository.save(likeModel);
        return getAggregatedLikes(postId);
    }

    public LikeDto getLikesByPostId(String postId) {
        log.info("Getting likes for post id: {}", postId);
        return getAggregatedLikes(postId);
    }

    public void removeLike(String likeId) {
        log.info("Removing like with id: {}", likeId);
        likeRepository.deleteById(likeId);
    }

    private LikeDto getAggregatedLikes(String postId) {
        List<LikeModel> likeModels = likeRepository.findByPostId(postId);

        LikeDto likeDto = new LikeDto();
        likeDto.setNumberOfLikes(likeModels.size());
        likeDto.setUsernames(likeModels.stream().map(LikeModel::getId).collect(Collectors.toList()));

        return likeDto;
    }
}
