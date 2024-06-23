package org.spring.searchservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.searchservice.events.UserRegistrationEvent;
import org.spring.searchservice.models.UserModel;
import org.spring.searchservice.repositories.UserRepository;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSearchService {

	private final UserRepository userRepository;

	public void saveUser(UserRegistrationEvent userRegistrationEvent) {
		UserModel userModel = UserModel.builder()
				.id(userRegistrationEvent.getId())
				.username(userRegistrationEvent.getUsername())
				.email(userRegistrationEvent.getEmail())
				.profilePicture(userRegistrationEvent.getProfilePicture())
				.firstName(userRegistrationEvent.getFirstName())
				.lastName(userRegistrationEvent.getLastName())
				.build();
		userModel.prePersist();

		log.info("Saving user: {}", userModel);

		userRepository.save(userModel);
	}

	public List<UserModel> searchUser(String query) {
		return userRepository.findByUsernameOrFirstNameOrLastNameOrEmail(query);
	}

	public List<UserModel> getSearchHitsResults(String query , int page, int size) {
		return userRepository.suggest(query).getSearchHits().stream()
				.map(SearchHit::getContent)
				.toList();
	}



}
