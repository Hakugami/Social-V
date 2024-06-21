package org.spring.FriendService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE")
public interface UserClient {

    @GetMapping("/api/v1/users/exists/{username}")
    ResponseEntity<Boolean> doesUserExist(@PathVariable("username") String username);
}