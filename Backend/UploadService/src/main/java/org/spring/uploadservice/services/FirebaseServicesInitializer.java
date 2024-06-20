package org.spring.uploadservice.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseServicesInitializer {

    @Value("${firebase.keystore.path}")
    private String firebaseKeystorePath;

    @Value("${firebase.bucket.name}")
    private String firebaseBucketName;

    @PostConstruct
    public void initServices() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(firebaseKeystorePath);
        FirebaseOptions options =  FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(firebaseBucketName)
                .build();
        // Check if FirebaseApp is already initialized to prevent reinitialization
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
