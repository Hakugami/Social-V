package org.spring.FriendService.repositories;

import org.spring.FriendService.models.entities.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FriendshipRepository extends Neo4jRepository<Friendship, String> {
    @Query(value = "MATCH (u:UserReference {id: $userId})-[:FRIEND_OF]-(f:Friendship)-[:FRIEND_OF]-(friend:UserReference) " +
            "RETURN DISTINCT friend.id SKIP $skip LIMIT $limit",
            countQuery = "MATCH (u:UserReference {id: $userId})-[:FRIEND_OF]-(f:Friendship)-[:FRIEND_OF]-(friend:UserReference) " +
                    "RETURN count(DISTINCT friend)")
    Page<String> findFriendIdsByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("MATCH (u:UserReference {id: $userId})-[:FRIEND_OF]-(f:Friendship)-[:FRIEND_OF]-(friend:UserReference)-[:FRIEND_OF]-(friendship:Friendship)-[:FRIEND_OF]-(mutualFriend:UserReference) " +
            "WHERE NOT (u)-[:FRIEND_OF]-(:Friendship)-[:FRIEND_OF]-(mutualFriend) AND mutualFriend.id <> u.id " +
            "RETURN DISTINCT mutualFriend.id AS recommendedFriendId LIMIT $limit")
    List<String> findRecommendedFriendIds(@Param("userId") String userId, @Param("limit") int limit);


    @Query("MATCH (u:UserReference {id: $userId})-[:FRIEND_OF]-(f:Friendship)-[:FRIEND_OF]-(friend:UserReference)-[:FRIEND_OF]-(friendship:Friendship)-[:FRIEND_OF]-(secondDegreeFriend:UserReference) " +
            "WHERE NOT (u)-[:FRIEND_OF]-(:Friendship)-[:FRIEND_OF]-(secondDegreeFriend) AND secondDegreeFriend.id <> u.id " +
            "RETURN DISTINCT secondDegreeFriend.id AS recommendedFriendId LIMIT $limit")
    List<String> findSecondDegreeConnections(@Param("userId") String userId, @Param("limit") int limit);
}
