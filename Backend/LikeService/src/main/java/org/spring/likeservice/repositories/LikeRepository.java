package org.spring.likeservice.repositories;

import org.spring.likeservice.models.LikeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends MongoRepository<LikeModel, String> {

    List<LikeModel> findByPostId(String postId);


    //get all like by username and post id
    Optional<LikeModel> findByUsernameAndPostId(String username, String postId);

}
