package org.spring.commentservice.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
public class CommentModel {
    @MongoId
    private String id;
    private String postId;
    private String userId;
    private String username;
    private String content;
}
