package org.spring.postservice.clients;

import org.spring.postservice.configs.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "FRIENDSERVICE", configuration = FeignClientConfiguration.class)
public interface FriendServiceClient {
        @GetMapping("/api/v1/friends/all/{userId}")
        List<String> getAllFriendUsernames(@PathVariable String userId);

}
