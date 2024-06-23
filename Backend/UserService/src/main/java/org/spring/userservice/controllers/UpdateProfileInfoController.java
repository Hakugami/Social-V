package org.spring.userservice.controllers;


import jakarta.validation.Valid;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;
import org.apache.catalina.User;
import org.spring.userservice.mappers.UserModelMapper;
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
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET, RequestMethod.PUT},allowedHeaders = "*") // Replace with your Angular app's URL
public class UpdateProfileInfoController {

    private final UserModelRepository userModelRepo;

    @GetMapping("{email}")
    public ResponseEntity<Object> getUserData(@PathVariable String email) {
        try {
            UserModelDto userModelDto = userModelRepo.findUserByEmail(email);
            if (userModelDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this email");
            }
            return ResponseEntity.ok(userModelDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching user data");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModelDto comingUserModelDto) {
         Optional<UserModel> userModel =   userModelRepo.findByEmail(comingUserModelDto.email());
            if (userModel.isPresent()) {
                updateUserFields(userModel.get(), comingUserModelDto);
                userModelRepo.save(userModel.get());
                return ResponseEntity.ok("Profile updated successfully");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
    }

    private void updateUserFields(UserModel user, UserModelDto updatedDto) {
        if (updatedDto.username() != null) user.setUsername(updatedDto.username());
        if (updatedDto.email() != null) user.setEmail(updatedDto.email());
        if (updatedDto.status() != null) user.setStatus(updatedDto.status());
        if (updatedDto.firstName() != null) user.setFirstName(updatedDto.firstName());
        if (updatedDto.lastName() != null) user.setLastName(updatedDto.lastName());
        if (updatedDto.address() != null) user.setAddress(updatedDto.address());
        if (updatedDto.gender() != null) user.setGender(updatedDto.gender());
        if (updatedDto.country() != null) user.setCountry(updatedDto.country());
        if (updatedDto.city() != null) user.setCity(updatedDto.city());
        if (updatedDto.birthDate() != null) user.setBirthDate(updatedDto.birthDate());
        if (updatedDto.phoneNumber() != null) user.setPhoneNumber(updatedDto.phoneNumber());
        if (updatedDto.profilePicture() != null) user.setProfilePicture(updatedDto.profilePicture());
        if (updatedDto.coverPicture() != null) user.setCoverPicture(updatedDto.coverPicture());
        if (updatedDto.url() != null) user.setUrl(updatedDto.url());
    }

    @Autowired
    public UpdateProfileInfoController(UserModelRepository userModelRepo) {
        this.userModelRepo = userModelRepo;
    }
}