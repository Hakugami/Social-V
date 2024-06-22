package org.spring.FriendService.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.FriendService.models.enums.FriendRequestStatus;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class FriendRequest {
    @Id
    private String id;
    @Property("status")
    private FriendRequestStatus status;

    @Property("created_at")
    private Instant timestamp;

    @Relationship(type = "REQUESTED_BY", direction = Relationship.Direction.INCOMING)
    private UserReference requester;

    @Relationship(type = "REQUESTED_TO", direction = Relationship.Direction.OUTGOING)
    private UserReference recipient;
}