package org.spring.commentservice.repositories;

import org.spring.commentservice.models.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentModel, String> {
    public CommentModel getByPostId(String postId);

}
