package org.spring.userservice.controllers;


import lombok.RequiredArgsConstructor;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UpdateProfileInfoController {

	private final UserService userService;

	@GetMapping("{username}")
	public ResponseEntity<Object> getUserData(@PathVariable String username) {
		try {
			UserModelDto userModelDto = userService.getByUsername(username);
			return ResponseEntity.ok(userModelDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this username !!");
		}
	}

	@PutMapping
	public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModelDto comingUserModelDto) {
		try {
			UserModel userModel = userService.updateProfileInfo(comingUserModelDto);
			return ResponseEntity.ok(userModel);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}
}