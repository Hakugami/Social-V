package org.spring.FriendService.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.FriendService.clients.UserClient;
import org.spring.FriendService.models.dtos.UserModelDTO;
import org.spring.FriendService.repositories.FriendshipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserClient userClient;

    public List<UserModelDTO> getFriends(String userId, Integer page, Integer size){
        Page<String> friendIds = friendshipRepository.findFriendIdsByUserId(userId, PageRequest.of(page, size));
        List<UserModelDTO> friends = userClient.getUsersByEmailsOrUsernames(friendIds.getContent());
        return friends;
    }


}
