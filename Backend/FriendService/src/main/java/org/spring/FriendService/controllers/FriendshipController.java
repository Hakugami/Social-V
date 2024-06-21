package org.spring.FriendService.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.FriendService.models.dtos.FriendRequestDTO;
import org.spring.FriendService.services.FriendRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {

    private static final Logger log = LoggerFactory.getLogger(FriendshipController.class);
    private final FriendRequestService friendRequestService;

    public FriendshipController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/request")
    @ApiResponse(description = "send a friend request",responseCode = "201")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody FriendRequestDTO request) {
        friendRequestService.sendFriendRequest(request.getRequesterId(), request.getRecipientId());
        log.info("in the endpoint , data is"+ request.getRecipientId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    @ApiResponse(description = "method",responseCode = "200")
    public ResponseEntity<Void> acceptFriendRequest(@RequestParam String requestId) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }
}
