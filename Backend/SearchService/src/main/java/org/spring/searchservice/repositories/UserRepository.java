package org.spring.searchservice.repositories;

import org.spring.searchservice.models.UserModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<UserModel, String> {

	@Query("{\"bool\": {\"should\": [{\"match\": {\"username\": \"?0\"}}, {\"match\": {\"firstName\": \"?0\"}}, {\"match\": {\"lastName\": \"?0\"}}, {\"match\": {\"email\": \"?0\"}}]}}")
	List<UserModel> findByUsernameOrFirstNameOrLastNameOrEmail(String query);

	@Query("{\"bool\": {\"should\": [{\"match_phrase_prefix\": {\"combined\": \"?0\"}}]}}")
	SearchHits<UserModel> suggest(String suggestion);
}
