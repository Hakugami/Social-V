package org.spring.userservice.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.spring.userservice.clients.UploadClient;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UpdateProfileInfoController {

	private final UserService userService;
	private final ObjectMapper objectMapper;
	private final UploadClient uploadClient;

	@GetMapping("{username}")
	@ApiResponse(description = "Get user data by username", responseCode = "200")
	public ResponseEntity<Object> getUserData(@PathVariable String username) {
		try {
			UserModelDto userModelDto = userService.getByUsername(username);
			return ResponseEntity.ok(userModelDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this username !!");
		}
	}

	@PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiResponse(description = "Update user profile info", responseCode = "200")
	public ResponseEntity<Object> updateProfileInfo(@RequestPart("dto") String dtoJson,
	                                                @RequestPart(value = "file", required = false) MultipartFile image) {
		try {
			UserModelDto comingUserModelDto = objectMapper.readValue(dtoJson, UserModelDto.class);
			String url = null;
			if (image != null && !image.isEmpty() && Objects.requireNonNull(image.getContentType()).startsWith("image")) {
				url = uploadClient.uploadFile(image);
			}
			UserModel userModel = userService.updateProfileInfo(comingUserModelDto, url);
			return ResponseEntity.ok(userModel);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}
}