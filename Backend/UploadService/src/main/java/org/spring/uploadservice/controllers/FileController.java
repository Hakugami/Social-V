package org.spring.uploadservice.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.uploadservice.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

	private final FirebaseService firebaseService;

	@PostMapping("/upload")
	@ApiResponse(description = "Upload file to Firebase Storage", responseCode = "201")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			String fileUrl = firebaseService.uploadFileToFirebase(file);
			log.info("File uploaded successfully: {}", fileUrl);
			return ResponseEntity.ok(fileUrl);
		} catch (IOException e) {
			log.error("File upload failed: {}", e.getMessage());
			return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
		}
	}
}
