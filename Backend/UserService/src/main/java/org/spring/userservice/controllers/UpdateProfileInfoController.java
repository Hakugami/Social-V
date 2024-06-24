package org.spring.userservice.controllers;



import org.spring.userservice.mappers.UserModelMapper;
import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;
import org.spring.userservice.repositories.UserModelRepository;
import org.spring.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile/edit")
@CrossOrigin(origins = "http://localhost:4200") // This can be set per controller or endpoint as well
public class UpdateProfileInfoController {

    private final UserModelRepository userModelRepo;
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<Object> getUserData(@PathVariable String username) {
        try{
           UserModel userModel = userModelRepo.findUserModelByUsername(username);
           UserModelDto userModelDto = UserModelMapper.fromModelToDto(userModel);
            return ResponseEntity.ok(userModelDto);
        } catch (Exception e){
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this username !!");
          }
    }

    @PutMapping
    public ResponseEntity<Object> updateProfileInfo(@RequestBody UserModelDto comingUserModelDto) {
        try {
         UserModel userModel =   userModelRepo.findUserModelByEmail(comingUserModelDto.email());
        userModel = userService.updateCurrentUserModel(comingUserModelDto,userModel);
         userModelRepo.save(userModel);
         return ResponseEntity.ok("Profile updated successfully");
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
    }

    @Autowired
    public UpdateProfileInfoController(UserModelRepository userModelRepo,UserService userService) {
        this.userModelRepo = userModelRepo;
        this.userService=userService;
    }
}