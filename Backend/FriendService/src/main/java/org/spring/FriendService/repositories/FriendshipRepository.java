package org.spring.FriendService.repositories;

import org.spring.FriendService.models.entities.Friendship;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface FriendshipRepository extends Neo4jRepository<Friendship, String> {
}
