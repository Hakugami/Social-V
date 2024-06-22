package org.spring.FriendService.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.FriendService.clients.UserClient;
import org.spring.FriendService.exceptions.FriendRequestAlreadyExistsException;
import org.spring.FriendService.exceptions.FriendRequestNotFoundException;
import org.spring.FriendService.exceptions.UserNotFoundException;
import org.spring.FriendService.models.dtos.FriendRequestDTO;
import org.spring.FriendService.models.entities.FriendRequest;
import org.spring.FriendService.models.entities.Friendship;
import org.spring.FriendService.models.entities.UserReference;
import org.spring.FriendService.models.enums.FriendRequestStatus;
import org.spring.FriendService.repositories.FriendRequestRepository;
import org.spring.FriendService.repositories.FriendshipRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserClient userClient;
    private final FriendshipRepository friendshipRepository;


    public void sendFriendRequest(String requesterId, String recipientId) {
        if (!userClient.doesUserExist(requesterId).getBody() || !userClient.doesUserExist(recipientId).getBody()) {
            throw new UserNotFoundException("User not found");
        }

        if (friendRequestRepository.existsByRequesterIdAndRecipientId(requesterId, recipientId)) {
            throw new FriendRequestAlreadyExistsException("Friend request already exists");
        }

        FriendRequest friendRequest = new FriendRequest(
                UUID.randomUUID().toString(),
                FriendRequestStatus.PENDING,
                Instant.now(),
                new UserReference(requesterId),
                new UserReference(recipientId)
        );
        friendRequestRepository.save(friendRequest);
    }



    public void acceptFriendRequest(String requestId) {
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(requestId);
        if (!friendRequest.isPresent()) {
            throw new FriendRequestNotFoundException("Friend request not found");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();// Assuming the username field in UserDetails is used to store the user ID.
        log.info(username+"this is the id of the current user");
        System.out.println("----------------------------------");
        System.out.println(username);
//        // Check if the current user is the recipient of the friend request.
//        if (!friendRequest.get().getRecipient().getId().equals(currentUserId)) {
//            throw new IllegalArgumentException("The current user is not the recipient of the friend request");
//        }
        Friendship friendship = new Friendship(
                UUID.randomUUID().toString(),
                Instant.now(),
                new UserReference(friendRequest.get().getRequester().getId()),
                new UserReference(friendRequest.get().getRecipient().getId())

        );
        friendshipRepository.save(friendship);
        friendRequestRepository.deleteById(requestId);
    }

    public List<FriendRequestDTO> getFriendRequests(String userId) {
        List<FriendRequest> friendRequests = friendRequestRepository.findByRecipientId(userId);
        List<FriendRequestDTO> friendRequestDTOs = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            friendRequestDTOs.add(new FriendRequestDTO(friendRequest.getRequester().getId(), friendRequest.getRecipient().getId()));
        }
        return friendRequestDTOs;
    }
}
