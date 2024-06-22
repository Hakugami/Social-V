package org.spring.FriendService.clients;

import org.spring.FriendService.models.dtos.UserModelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/v1/users/exists/{username}")
    ResponseEntity<Boolean> doesUserExist(@PathVariable("username") String username);

    @GetMapping("/api/v1/users/byEmail")
    List<UserModelDTO> getUsersByEmailsOrUsernames(@RequestParam List<String> emails);


    @GetMapping("/api/v1/users/byEmail/{email}")
    UserModelDTO getUserDataByEmail(@PathVariable("email") String email);
}




