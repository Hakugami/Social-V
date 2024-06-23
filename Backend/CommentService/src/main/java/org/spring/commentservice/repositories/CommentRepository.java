package org.spring.commentservice.repositories;

import org.spring.commentservice.models.CommentModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentModel, String> {
	List<CommentModel> getByPostId(String postId , PageRequest pageRequest);

}
