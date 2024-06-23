package org.spring.userservice.controllers;


import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;
import org.apache.catalina.User;
import org.spring.userservice.models.Dtos.AuthModelDto;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("profile/edit")
@CrossOrigin(origins = "http://localhost:4200") // Replace with your Angular app's URL
public class UpdateProfileInfoController {
    private UserModelRepository userModelRepo;

    @GetMapping("{email}")
    public ResponseEntity<Object> getUserData(@PathVariable String email){
        try{
            UserModelDto userModelDto = userModelRepo.findUserByEmail(email);
            System.out.println(userModelDto.email());
            return ResponseEntity.ok(userModelDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this email");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModelDto comingUserModelDto) {
        /**
         * 1. Get user Model from DB .
         * 2. Check if there is any thing Changed .
         */
        UserModelDto userModelDto = userModelRepo.findUserByEmail(comingUserModelDto.email());
        try{
            userModelRepo.save(comingUserModelDto);
            return ResponseEntity.ok("Profile updated successfully");

        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Autowired
    public UpdateProfileInfoController(UserModelRepository userModelRepo) {
        this.userModelRepo = userModelRepo;
    }

}

