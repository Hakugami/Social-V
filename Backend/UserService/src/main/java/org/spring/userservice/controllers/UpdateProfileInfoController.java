package org.spring.userservice.controllers;


import com.netflix.discovery.converters.Auto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.repositories.UserModelRepository;
import org.spring.userservice.services.FirebaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("profile/edit")
public class UpdateProfileInfoController {
    private UserModelRepository userModelRepo;
    private FirebaseServices firebaseServices;

    @GetMapping
    public String get() {
        return "Update Profile Info";
    }


    @PostMapping
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile uploadedImage) {
        System.out.println("Image Received");
        try {
            firebaseServices.uploadImageToFirebase(uploadedImage);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @PutMapping
    public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModel comingUserModel) {
        /**
         * 1. Get user Model from DB .
         * 2. Check if there is any thing Changed .
         */
        Optional<UserModel> storedUserModel = userModelRepo.findById(comingUserModel.getId());
        // TODO - AMG -  There is a critical issue here - need to check with id , email , password and username.
        if (!storedUserModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User with this ID !!");
        } else {
            if (storedUserModel.get().equals(comingUserModel))
                return ResponseEntity.ok("No Change Happened from Client Side");
            else {
                userModelRepo.save(comingUserModel);
                return ResponseEntity.ok("Profile updated successfully");
            }
        }
    }

    private boolean isUserDataChanged(UserModel comingUserModel, UserModel currentUserModel) {
        return currentUserModel.equals(comingUserModel);
    }

    @Autowired
    public UpdateProfileInfoController(UserModelRepository userModelRepo) {
        this.userModelRepo = userModelRepo;
    }


    @Autowired
    public void setFirebaseServices(FirebaseServices firebaseServices) {
        this.firebaseServices = firebaseServices;
    }


}

