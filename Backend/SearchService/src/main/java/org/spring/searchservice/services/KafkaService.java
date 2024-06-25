package org.spring.searchservice.services;

import lombok.RequiredArgsConstructor;
import org.spring.searchservice.events.UserRegistrationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
	private final UserSearchService userSearchService;

	@KafkaListener(topics = "user-topic", groupId = "search-service")
	public void listenUserRegistrationEvent(UserRegistrationEvent userRegistrationEvent) {
		userSearchService.saveUser(userRegistrationEvent);
	}
}
