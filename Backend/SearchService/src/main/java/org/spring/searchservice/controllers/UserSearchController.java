package org.spring.searchservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.searchservice.events.UserRegistrationEvent;
import org.spring.searchservice.models.SearchQueryDto;
import org.spring.searchservice.models.UserModel;
import org.spring.searchservice.services.UserSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/search")
public class UserSearchController {

	private final UserSearchService userSearchService;

	@PostMapping("/users")
	public List<UserModel> searchUser(@RequestBody SearchQueryDto query , @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return userSearchService.getSearchHitsResults(query.getQuery(), page, size);

	}

	@GetMapping("/autocomplete")
	public List<UserModel> autocomplete(@RequestParam SearchQueryDto query , @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		return userSearchService.getSearchHitsResults(query.getQuery(), page, size);
	}

	@PostMapping("/save")
	public ResponseEntity<Boolean> saveUser(@RequestBody UserRegistrationEvent userModel) {
		userSearchService.saveUser(userModel);
		return ResponseEntity.ok(true);
	}
}
