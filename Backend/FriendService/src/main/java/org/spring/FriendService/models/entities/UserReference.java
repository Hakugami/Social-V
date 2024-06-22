package org.spring.FriendService.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class UserReference {
    @Id
    private String id;

}
