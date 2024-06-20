package org.spring.userservice.services;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseServices {

    @Value("${firebase.bucket.name}")
    private String firebaseBucketName;

    public String uploadImageToFirebase(MultipartFile file) throws IOException {
        // Get a reference to the Firebase Storage bucket
        Bucket bucket = StorageClient.getInstance().bucket();
        // Generate a unique file name , we maybe change it to the name of the user if we need
        String fileName = UUID.randomUUID().toString() + "." + getFileExtension(file.getOriginalFilename());
        // Upload the file to Firebase Storage
        bucket.create(fileName, file.getBytes(), file.getContentType());
        // Get the public download URL of the uploaded file
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex >= 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}
