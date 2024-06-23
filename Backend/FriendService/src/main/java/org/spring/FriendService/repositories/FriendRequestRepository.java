package org.spring.FriendService.repositories;

import org.spring.FriendService.models.dtos.FriendRequestDTO;
import org.spring.FriendService.models.entities.FriendRequest;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface FriendRequestRepository extends Neo4jRepository<FriendRequest, String> {
    List<FriendRequest> findByRequesterId(String requesterId);
    List<FriendRequest> findByRecipientId(String recipientId);
    boolean existsByRequesterIdAndRecipientId(String requesterId, String recipientId);
}
