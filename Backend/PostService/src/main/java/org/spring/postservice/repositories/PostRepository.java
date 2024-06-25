package org.spring.postservice.repositories;

import org.spring.postservice.models.PostModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends MongoRepository<PostModel, String> {
	List<PostModel> findByUserId(String id, PageRequest of);

	Collection<PostModel> findAllBy(PageRequest of);

	List<PostModel> findByIdAndType(String id, PageRequest of, String type);

	List<PostModel> findByUsername(String username, PageRequest of);
}
