package org.spring.likeservice.repositories;

import org.spring.likeservice.models.LikeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepository extends MongoRepository<LikeModel, String> {

    List<LikeModel> findByPostId(String postId);

}
