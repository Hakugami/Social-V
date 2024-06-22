package org.spring.FriendService.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.FriendService.clients.UserClient;
import org.spring.FriendService.models.dtos.UserModelDTO;
import org.spring.FriendService.repositories.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {


    private final FriendshipRepository friendshipRepository;
    private final UserClient userClient;

    public List<UserModelDTO> getRecommendedFriends(String userId, int limit) {
        List<String> userIds=friendshipRepository.findRecommendedFriendIds(userId, limit);
       List<UserModelDTO> recommendedFriends = userClient.getUsersByEmailsOrUsernames(userIds);
        return recommendedFriends;
    }

    public List<UserModelDTO> getSecondDegreeConnections(String userId, int limit) {
        List<String> userIds=friendshipRepository.findSecondDegreeConnections(userId, limit);
        List<UserModelDTO> recommendedFriends = userClient.getUsersByEmailsOrUsernames(userIds);
        return recommendedFriends;
    }
}
