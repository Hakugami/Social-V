package org.spring.userservice.controllers;


import org.spring.userservice.mappers.UserModelMapper;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile/edit")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UpdateProfileInfoController {

	private final UserModelRepository userModelRepo;

	@Autowired
	public UpdateProfileInfoController(UserModelRepository userModelRepo) {
		this.userModelRepo = userModelRepo;
	}

	@GetMapping("{email}")
	public ResponseEntity<Object> getUserData(@PathVariable String email) {
		try {
			UserModel userModel = userModelRepo.findUserModelByEmail(email);
			UserModelDto userModelDto = UserModelMapper.fromModelToDto(userModel);
			return ResponseEntity.ok(userModelDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this email");
		}
	}

	@PutMapping
	public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModelDto comingUserModelDto) {
		try {
			UserModel userModel = userModelRepo.findUserModelByEmail(comingUserModelDto.email());
			userModelRepo.save(userModel);
			return ResponseEntity.ok("Profile updated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}
}