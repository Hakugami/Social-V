package org.spring.FriendService.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.FriendService.models.dtos.FriendRequestDTO;
import org.spring.FriendService.models.dtos.FriendRequestNotificationDTO;
import org.spring.FriendService.models.dtos.UserModelDTO;
import org.spring.FriendService.services.FriendRequestService;
import org.spring.FriendService.services.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Slf4j
public class FriendshipController {

    private final FriendRequestService friendRequestService;
    private final FriendshipService friendshipService;


    @PostMapping("/request")
    @ApiResponse(description = "send a friend request",responseCode = "201")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody FriendRequestDTO request) {
        friendRequestService.sendFriendRequest(request.requesterId(), request.recipientId());
        log.info("in the endpoint , data is"+ request.recipientId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/request/{userId}")
    public ResponseEntity<List<FriendRequestNotificationDTO>> getFriendRequest(@PathVariable String userId) {
        List<FriendRequestNotificationDTO> friendRequest = friendRequestService.getFriendRequests(userId);
        return ResponseEntity.ok(friendRequest);
    }

    @PostMapping("/accept")
    @ApiResponse(description = "method",responseCode = "200")
    public ResponseEntity<Void> acceptFriendRequest(@RequestParam String requestId) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    @ApiResponse(description = "get all the friends of a user",responseCode = "200")
    public ResponseEntity<List<UserModelDTO>> getFriends(@PathVariable String userId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        List<UserModelDTO> friends = friendshipService.getFriends(userId, page, size);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/pending/{userId}")
    @ApiResponse(description = "get all the pending friend requests of a user",responseCode = "200")
    public ResponseEntity<List<FriendRequestDTO>> getPendingFriendRequests(@PathVariable String userId) {
        List<FriendRequestDTO> friends = friendRequestService.getFriendRequestsByRequesterId(userId);
        return ResponseEntity.ok(friends);
    }



    @DeleteMapping("/request/{requestId}")
    @ApiResponse(description = "delete a friend request",responseCode = "200")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable String requestId) {
        friendRequestService.deleteFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(description = "delete a friend",responseCode = "200")
    public ResponseEntity<Void> deleteFriend(@PathVariable String userId, @RequestParam String friendId) {
       if(friendshipService.deleteFriend(userId, friendId)){
           return ResponseEntity.ok().build();
         }
        return ResponseEntity.notFound().build();
    }


}
