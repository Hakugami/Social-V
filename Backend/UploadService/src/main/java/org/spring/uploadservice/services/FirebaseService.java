package org.spring.uploadservice.services;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class FirebaseService {

	private static final String[] ALLOWED_CONTENT_TYPES = {
			"image/jpeg", "image/png", "image/gif"
			, "video/mp4", "video/quicktime", "video/x-ms-wmv"
			, "video/x-msvideo", "video/x-flv", "video/webm"
			, "video/mkv", "video/3gpp", "video/3gpp2"
			, "video/avi", "video/ogg", "video/ogv"
			, "video/mov", "video/mpeg", "video/mpg"
			, "video/x-matroska", "video/x-ms-wmv"
	};
	@Value("${firebase.bucket.name}")
	private String firebaseBucketName;

	public String uploadFileToFirebase(MultipartFile file) throws IOException {
		// Validate file
		validateFile(file);
		// Upload file to Firebase Storage
		Bucket bucket = StorageClient.getInstance().bucket();
		// Generate unique file name
		String fileName = UUID.randomUUID().toString() + "." + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
		// Upload file to Firebase Storage
		bucket.create(fileName, file.getBytes(), file.getContentType());
		// Return file URL
		return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);
	}

	private void validateFile(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IOException("File is empty");
		}

		String contentType = file.getContentType();
		if (!isAllowedContentType(contentType)) {
			throw new IOException("Invalid file type: " + contentType);
		}
	}

	private boolean isAllowedContentType(String contentType) {
		for (String allowedContentType : ALLOWED_CONTENT_TYPES) {
			if (allowedContentType.equals(contentType)) {
				return true;
			}
		}
		return false;
	}

	private String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex >= 0 && lastDotIndex < fileName.length() - 1) {
			return fileName.substring(lastDotIndex + 1);
		}
		return "";
	}
}
