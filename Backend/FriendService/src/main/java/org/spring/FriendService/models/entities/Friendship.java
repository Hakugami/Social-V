package org.spring.FriendService.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Friendship {
    @Id
    private String id;

    @Property("created_at")
    private Instant createdAt;

    @Relationship(type = "FRIEND_OF", direction = Relationship.Direction.INCOMING)
    private UserReference user1;

    @Relationship(type = "FRIEND_OF", direction = Relationship.Direction.OUTGOING)
    private UserReference user2;

}
