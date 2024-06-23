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
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UpdateProfileInfoController {

    private final UserModelRepository userModelRepo;

    @GetMapping("{email}")
    public ResponseEntity<Object> getUserData(@PathVariable String email) {
            Optional<UserModelDto> userModelDto = userModelRepo.findUserModelDtoByEmail(email);
            if (userModelDto.isPresent()) return ResponseEntity.ok(userModelDto.get());
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this email");
    }

    @PutMapping
    public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModelDto comingUserModelDto) {
         Optional<UserModel> userModel =   userModelRepo.findUserModelByEmail(comingUserModelDto.email());
            if (userModel.isPresent()) {
                updateUserModelFields(userModel.get(), comingUserModelDto);
                userModelRepo.save(userModel.get());
                return ResponseEntity.ok("Profile updated successfully");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
    }

    private void updateUserModelFields(UserModel user, UserModelDto updatedDto) {
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